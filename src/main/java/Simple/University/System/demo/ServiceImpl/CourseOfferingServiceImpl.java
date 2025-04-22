package Simple.University.System.demo.ServiceImpl;

import Simple.University.System.demo.Entity.Course;
import Simple.University.System.demo.Entity.CourseOffering;
import Simple.University.System.demo.Entity.Teacher;
import Simple.University.System.demo.Exception.BusinessException;
import Simple.University.System.demo.Extra.DTO.CourseOfferInput;
import Simple.University.System.demo.Extra.DTO.CourseOfferingResponseDTO;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Mapper.CourseOfferingMapper;
import Simple.University.System.demo.Repository.CourseOfferingRepository;
import Simple.University.System.demo.Repository.CourseRepository;
import Simple.University.System.demo.Repository.TeacherRepository;
import Simple.University.System.demo.Service.CourseOfferingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

// --- CourseOfferingServiceImpl.java ---
@Service
@Transactional
@RequiredArgsConstructor
public class CourseOfferingServiceImpl implements CourseOfferingService {

    private final CourseOfferingRepository offeringRepo;
    private final CourseRepository courseRepo;
    private final TeacherRepository teacherRepo;
    private final CourseOfferingMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(CourseOfferingServiceImpl.class);

    private CourseOffering findOfferOrThrow(Long id) {
        return offeringRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id = " + id));
    }

    @Override
    public CourseOfferingResponseDTO addOffer(@Valid CourseOfferInput dto) {
        Course course = courseRepo.findById(dto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found id=" + dto.getCourseId()));
        Teacher teacher = teacherRepo.findById(dto.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found id=" + dto.getTeacherId()));
        logger.info("teacher id = {} , course id {}", teacher.getId(), course.getId());

        if (offeringRepo.existsByCourseAndSemesterAndYear(
                course,
                dto.getSemester(),
                dto.getYear())) {
            throw new BusinessException(
                    String.format("Course %s already offered in %s %d",
                            course.getCode(), dto.getSemester(), dto.getYear()));
        }

        if (offeringRepo.existMoreThanFourByTeacher_IdAndYearAndSemester(teacher.getId(), dto.getYear(), dto.getSemester())) {
            throw new BusinessException(
                    String.format("Teacher %d cannot teach more than 4 courses in %s %d",
                            teacher.getId(), dto.getSemester(), dto.getYear()));
        }

        CourseOffering offering = CourseOffering.builder()
                .course(course)
                .teacher(teacher)
                .semester(dto.getSemester())
                .year(dto.getYear())
                .build();
        CourseOffering saved = offeringRepo.save(offering);

        logger.info("teacher {} registered successfully to course {} in semester {} in the year {}",
                teacher.getName(),
                course.getTitle(),
                dto.getSemester(),
                dto.getYear());

        return mapper.toResponseDto(saved);
    }

    @Override
    public CourseOfferingResponseDTO updateOffer(Long id, @Valid CourseOfferInput dto) {
        CourseOffering offering = findOfferOrThrow(id);
        Course course = courseRepo.findById(dto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found id=" + dto.getCourseId()));
        Teacher teacher = teacherRepo.findById(dto.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found id=" + dto.getTeacherId()));


        boolean conflict = offeringRepo.existsByCourseAndSemesterAndYear(
                course, dto.getSemester(), dto.getYear());
        boolean isSameAsCurrent = course.getId().equals(offering.getCourse().getId()) &&
                dto.getSemester().equals(offering.getSemester()) &&
                dto.getYear() == offering.getYear();

        if (conflict && !isSameAsCurrent) {
            throw new BusinessException(String.format("Course %s already offered in %s %d",
                    course.getCode(), dto.getSemester(), dto.getYear()));
        }

        boolean moreThan4 = offeringRepo.existMoreThanFourByTeacher_IdAndYearAndSemester(teacher.getId(), dto.getYear(), dto.getSemester());

        boolean sameTeacherSameTime = teacher.getId().equals(offering.getTeacher().getId()) &&
                dto.getSemester().equals(offering.getSemester()) &&
                dto.getYear() == offering.getYear();

        if (moreThan4 && !sameTeacherSameTime) {
            throw new BusinessException(String.format("Teacher %d cannot teach more than 4 courses in %s %d",
                    teacher.getId(), dto.getSemester(), dto.getYear()));
        }

        // Full update
        offering.setCourse(course);
        offering.setTeacher(teacher);
        offering.setSemester(dto.getSemester());
        offering.setYear(dto.getYear());

        CourseOffering updated = offeringRepo.save(offering);
        return mapper.toResponseDto(updated);
    }

    @Override
    public CourseOfferingResponseDTO getById(Long id) {
        CourseOffering offering = findOfferOrThrow(id);
        return mapper.toResponseDto(offering);
    }

    @Override
    public Boolean deleteOffer(Long id) {
        if (!offeringRepo.existsById(id)) {
            throw new EntityNotFoundException("Offering not found id=" + id);
        }
        offeringRepo.deleteById(id);
        logger.info("Deleted course offering with id {}", id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseOfferingResponseDTO> getAllOfferingsByYearAndSemester(Integer year, SemesterEnum semester) {
        List<CourseOffering> offerings;
        if (year != null) {
            offerings = (semester != null)
                    ? offeringRepo.findByYearAndSemester(year, semester)
                    : offeringRepo.findByYear(year);
        } else {
            offerings = offeringRepo.findAll();
        }
        return offerings.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }
}