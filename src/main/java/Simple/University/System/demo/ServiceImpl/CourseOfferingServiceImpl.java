package Simple.University.System.demo.ServiceImpl;

import Simple.University.System.demo.Entity.Course;
import Simple.University.System.demo.Entity.CourseOffering;
import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Entity.Teacher;
import Simple.University.System.demo.Extra.DTO.CourseOfferInput;
import Simple.University.System.demo.Extra.DTO.CourseOfferingResponseDTO;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Mapper.CourseOfferingMapper;
import Simple.University.System.demo.Repository.CourseOfferingRepository;
import Simple.University.System.demo.Repository.CourseRepository;
import Simple.University.System.demo.Repository.TeacherRepository;
import Simple.University.System.demo.Service.CourseOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

// --- CourseOfferingServiceImpl.java ---
@Service
@Transactional
@RequiredArgsConstructor
public class CourseOfferingServiceImpl implements CourseOfferingService {

    private final CourseOfferingRepository offeringRepo;
    private final CourseRepository courseRepo;
    private final TeacherRepository teacherRepo;
    private final CourseOfferingMapper mapper;

    @Override
    public CourseOfferingResponseDTO addOffer(@Valid CourseOfferInput dto) {
        Course course = courseRepo.findById(dto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found id=" + dto.getCourseId()));
        Teacher teacher = teacherRepo.findById(dto.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found id=" + dto.getTeacherId()));

        if (offeringRepo.existsByCourseAndSemesterAndYear(course, dto.getSemester(), dto.getYear())) {
            throw new RuntimeException(
                    String.format("Course %s already offered in %s %d",
                            course.getCode(), dto.getSemester(), dto.getYear()));
        }

        CourseOffering offering = CourseOffering.builder()
                .course(course)
                .teacher(teacher)
                .semester(dto.getSemester())
                .year(dto.getYear())
                .build();

        CourseOffering saved = offeringRepo.save(offering);
        return mapper.toResponseDto(saved);
    }

    @Override
    public CourseOfferingResponseDTO updateOffer(Long id, CourseOfferInput dto) {
        CourseOffering offering = offeringRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offering not found id=" + id));

        Course course = courseRepo.findById(dto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found id=" + dto.getCourseId()));
        Teacher teacher = teacherRepo.findById(dto.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found id=" + dto.getTeacherId()));


        boolean conflict = offeringRepo.existsByCourseAndSemesterAndYear(
                course, dto.getSemester(), dto.getYear());
        if (conflict &&
                !(offering.getSemester() == dto.getSemester() && offering.getYear().equals(dto.getYear()))) {
            throw new RuntimeException(
                    String.format("Course %s already offered in %s %d",
                            course.getCode(), dto.getSemester(), dto.getYear()));
        }

        // Full update
        offering.setCourse(course);
        offering.setTeacher(teacher);
        offering.setSemester(dto.getSemester());
        offering.setYear(dto.getYear());

        CourseOffering updated = offeringRepo.save(offering);
        return mapper.toResponseDto(updated);
    }
    // باقي الأساليب (deleteOffer, getAllOffers, getOfferById) تُنفّذ بأسلوب مماثل:
    // - deleteOffer: تحقق الوجود ثم repo.deleteById(id)
    // - getAllOffers: repo.findAll(Pageable) + map to DTO page
    // - getOfferById: repo.findById + map to DTO
}
