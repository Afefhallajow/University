package Simple.University.System.demo.Service;

import Simple.University.System.demo.Entity.Course;
import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.SemesterEnum;

import java.util.List;

public interface CourseService {
    Course addCourse(String code, String title);
    Course updateCourse(Long id, String code, String title);
    boolean deleteCourse(Long id);
    List<Course> getAllCourses();
    Course getCourseById(Long id);
    List<Course> getCoursesByTeacher(Long teacherId, Integer year, SemesterEnum semester);
}

