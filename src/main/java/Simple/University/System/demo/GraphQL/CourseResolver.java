package Simple.University.System.demo.GraphQL;

import Simple.University.System.demo.Entity.Course;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Service.CourseService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final CourseService courseService;

    public Course addCourse(String code, String title) {
        return courseService.addCourse(code, title);
    }

    public Course updateCourse(Long id, String code, String title) {
        return courseService.updateCourse(id, code, title);
    }

    public boolean deleteCourse(Long id) {
        return courseService.deleteCourse(id);
    }

    public List<Course> allCourses() {
        return courseService.getAllCourses();
    }
    public List<Course> getCoursesByTeacher(Long teacherId,
                                         Integer year,
                                         SemesterEnum semester) {
        return courseService.getCoursesByTeacher(teacherId, year, semester);
    }
}
