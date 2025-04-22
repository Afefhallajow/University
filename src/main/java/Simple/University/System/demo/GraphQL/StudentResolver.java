package Simple.University.System.demo.GraphQL;

import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.PageResponse.StudentPageResponse;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Extra.StudentReportResponse;
import Simple.University.System.demo.Service.StudentService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StudentResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final StudentService studentService;

    public Student addStudent(String name, String email, Map<String, Object> extra) {
        return studentService.addStudent(name, email, extra);
    }

    public Student updateStudent(Long id, String name, String email, Map<String, Object> extra) {
        return studentService.updateStudent(id, name, email, extra);
    }

    public boolean deleteStudent(Long id) {
        return studentService.deleteStudent(id);
    }

    public StudentPageResponse allStudents(int page, int size) {
        return studentService.getAllStudents(page, size);
    }

    public Student getStudentById(Long id){
        return studentService.getStudentById(id);
    }

    public StudentReportResponse getReport(Long studentId){return studentService.getReport(studentId);};

    public List<Student> studentsByCourse(Long courseId, SemesterEnum semester){
        return studentService.showStudentEnrollInCourseBySemester(courseId, semester);
    }
}