package Simple.University.System.demo.ServiceImpl;

import Simple.University.System.demo.Entity.Course;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Repository.CourseRepository;
import Simple.University.System.demo.Repository.TeacherRepository;
import Simple.University.System.demo.Service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    @Override
    public Course addCourse(String title, String code) {
        Course course = new Course();
        course.setCode(code);
        course.setTitle(title);
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, String title, String code) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        if (code != null) course.setCode(code);
        if (title != null) course.setTitle(title);
        return courseRepository.save(course);
    }

    @Override
    public boolean deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) return false;
        courseRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findEntityById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getCoursesByTeacher(Long teacherId, Integer year, SemesterEnum semester) {
        // تأكد من وجود المعلم
        teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found id=" + teacherId));

        if (year != null && semester != null) {
            return courseRepository
                    .findDistinctByOfferings_Teacher_IdAndOfferings_YearAndOfferings_Semester(
                            teacherId, year, semester);
        } else if (year != null) {
            return courseRepository
                    .findDistinctByOfferings_Teacher_IdAndOfferings_Year(teacherId, year);
        } else {
            return courseRepository.findDistinctByOfferings_Teacher_Id(teacherId);
        }
    }
}