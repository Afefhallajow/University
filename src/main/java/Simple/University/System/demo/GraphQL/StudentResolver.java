package Simple.University.System.demo.GraphQL;

import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Extra.StudentReportResponse;
import Simple.University.System.demo.Extra.StudentResponse;
import Simple.University.System.demo.Service.StudentService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final StudentService studentService;

    public Student addStudent(String name, String email) {
        return studentService.addStudent(name, email);
    }

    public Student updateStudent(Long id, String name, String email) {
        return studentService.updateStudent(id, name, email);
    }

    public boolean deleteStudent(Long id) {
        return studentService.deleteStudent(id);
    }

    public List<Student> allStudents() {
        return studentService.getAllStudents();
    }

    public StudentReportResponse getReport(Long id){return studentService.getReport(id);};

    public List<Student> studentsByCourse(Long courseId, SemesterEnum semester){
        return studentService.showStudentEnrollInCourseBySemester(courseId, semester);
    }
}