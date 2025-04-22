package Simple.University.System.demo.GraphQL;

import Simple.University.System.demo.Entity.Teacher;
import Simple.University.System.demo.Extra.PageResponse.TeacherPageResponse;
import Simple.University.System.demo.Service.TeacherService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TeacherResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final TeacherService teacherService;

    public Teacher addTeacher(String name, String email, Map<String, Object> extra) {
        return teacherService.addTeacher(name, email, extra);
    }

    public Teacher updateTeacher(Long id, String name, String email, Map<String, Object> extra) {
        return teacherService.updateTeacher(id, name, email, extra);
    }

    public boolean deleteTeacher(Long id) {
        return teacherService.deleteTeacher(id);
    }

    public TeacherPageResponse allTeachers(int page, int size) {
        return teacherService.getAllTeachers(page, size);
    }

    public Teacher getTeacherById(Long id){
        return teacherService.getTeacherById(id);
    }
}