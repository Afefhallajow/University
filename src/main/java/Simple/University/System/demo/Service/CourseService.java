package Simple.University.System.demo.Service;

import Simple.University.System.demo.Entity.Course;
import Simple.University.System.demo.Extra.PageResponse.CoursePageResponse;
import Simple.University.System.demo.Extra.SemesterEnum;

import java.util.List;
import java.util.Map;

public interface CourseService {
    Course addCourse(String code, String title, List<String> tags, Map<String, Object> metadata);
    public Course updateCourse(Long id, String code, String title, List<String> tags, Map<String, Object> metadata);
    boolean deleteCourse(Long id);
    CoursePageResponse getAllCourses(int page, int size);
    Course getCourseById(Long id);
    List<Course> getCoursesByTeacher(Long teacherId, Integer year, SemesterEnum semester);
}

