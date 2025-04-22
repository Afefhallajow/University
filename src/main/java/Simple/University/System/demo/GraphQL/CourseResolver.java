package Simple.University.System.demo.GraphQL;

import Simple.University.System.demo.Entity.Course;
import Simple.University.System.demo.Extra.PageResponse.CoursePageResponse;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Service.CourseService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CourseResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final CourseService courseService;

    public Course addCourse(String code, String title, List<String> tags, Map<String, Object> metadata) {
        return courseService.addCourse(code, title, tags, metadata);
    }

    public Course updateCourse(Long id, String code, String title, List<String> tags, Map<String, Object> metadata) {
        return courseService.updateCourse(id, code, title, tags, metadata);
    }

    public boolean deleteCourse(Long id) {
        return courseService.deleteCourse(id);
    }

    public CoursePageResponse allCourses(int page, int size) {
        return courseService.getAllCourses(page, size);
    }

    public Course getCourseById(Long id){
        return courseService.getCourseById(id);
    }

    public List<Course> getCoursesByTeacher(Long teacherId,
                                         Integer year,
                                         SemesterEnum semester) {
        return courseService.getCoursesByTeacher(teacherId, year, semester);
    }
}