package Simple.University.System.demo.GraphQL;

import Simple.University.System.demo.Entity.Enrollment;
import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Repository.StudentRepository;
import Simple.University.System.demo.Service.EnrollmentService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EnrollmentResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final EnrollmentService enrollmentService;
    private final StudentRepository studentRepository;

    public Enrollment enrollStudent(Long studentId,  Long offeringId) {
        return enrollmentService.enrollStudent(studentId, offeringId);
    }


    public Enrollment setMark(Long enrollmentId,  Integer mark) {
        return enrollmentService.setMark(enrollmentId, mark);
    }

    public List<Enrollment> allEnrollments() {
        return enrollmentService.findAll();
    }

    public Student studentReport( Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }
    public List<Enrollment> getEnrollmentsByStudentAndSemesterAndYear(
            Long studentId,
            SemesterEnum semester,
            int year){
        return enrollmentService.getEnrollmentsByStudentAndSemesterAndYear(studentId, semester, year);
    }
}