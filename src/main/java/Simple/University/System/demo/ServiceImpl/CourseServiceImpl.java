package Simple.University.System.demo.ServiceImpl;

import Simple.University.System.demo.Entity.Course;
import Simple.University.System.demo.Extra.PageResponse.CoursePageResponse;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Repository.CourseRepository;
import Simple.University.System.demo.Repository.TeacherRepository;
import Simple.University.System.demo.Service.CourseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    private Course findCourseOrThrow(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id = " + id));
    }
    @Override
    public Course addCourse(String title, String code, List<String> tags, Map<String, Object> metadata) {
        Course course = Course.builder()
                        .code(code)
                        .title(title)
                        .metadata(metadata)
                        .tags(tags != null ? tags : List.of())
                        .build();
        course = courseRepository.save(course);
        logger.info("Course added successfully with id: {}", course.getId());
        return course;
    }

    @Override
    public Course updateCourse(Long id, String code, String title, List<String> tags, Map<String, Object> metadata) {
        Course course = findCourseOrThrow(id);

        if (code != null && !code.trim().isEmpty()) course.setCode(code);
        if (title != null && !title.trim().isEmpty()) course.setTitle(title);

        Optional.ofNullable(metadata).ifPresent(course::setMetadata);

        if (tags != null) {
            course.setTags(tags);
        }
        course = courseRepository.save(course);
        logger.info("Course updated successfully with id: {}", course.getId());
        return course;
    }

    @Override
    public boolean deleteCourse(Long id) {
        Course course = findCourseOrThrow(id);
        courseRepository.delete(course);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public CoursePageResponse getAllCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> coursePage = courseRepository.findAll(pageable);
        return CoursePageResponse.fromPage(coursePage);
    }

    @Override
    @Transactional(readOnly = true)
    public Course getCourseById(Long id) {
        return findCourseOrThrow(id);
    }

    //_ show courses taught by specific teacher in [specific year, specific semester, all semesters]
    @Override
    @Transactional(readOnly = true)
    public List<Course> getCoursesByTeacher(Long teacherId, Integer year, SemesterEnum semester) {
        teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found id=" + teacherId));
        if (year == null) {
            if (semester != null) {
                return courseRepository
                        .findDistinctByOfferings_Teacher_IdAndOfferings_Semester(teacherId, semester);
            }
            return courseRepository.findDistinctByOfferings_Teacher_Id(teacherId);
        }

        return semester != null
                ? courseRepository.findDistinctByOfferings_Teacher_IdAndOfferings_YearAndOfferings_Semester(
                teacherId, year, semester)
                : courseRepository.findDistinctByOfferings_Teacher_IdAndOfferings_Year(
                teacherId, year);
    }
}