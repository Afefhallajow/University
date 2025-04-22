package Simple.University.System.demo.GraphQL;

import Simple.University.System.demo.Entity.Teacher;
import Simple.University.System.demo.Service.TeacherService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TeacherResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final TeacherService teacherService;

    public Teacher addTeacher(String name, String email) {
        return teacherService.addTeacher(name, email);
    }

    public Teacher updateTeacher(Long id, String name, String email) {
        return teacherService.updateTeacher(id, name, email);
    }

    public boolean deleteTeacher(Long id) {
        return teacherService.deleteTeacher(id);
    }

    public List<Teacher> allTeachers() {
        return teacherService.getAllTeachers();
    }
}