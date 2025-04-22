package Simple.University.System.demo.GraphQL;

import Simple.University.System.demo.Entity.CourseOffering;
import Simple.University.System.demo.Entity.Enrollment;
import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Repository.CourseOfferingRepository;
import Simple.University.System.demo.Repository.EnrollmentRepository;
import Simple.University.System.demo.Repository.StudentRepository;
import Simple.University.System.demo.Service.EnrollmentService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EnrollmentResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final EnrollmentService enrollmentService;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseOfferingRepository offeringRepository;

    public Enrollment enrollStudent(Long studentId,  Long offeringId) {
        return enrollmentService.enrollStudent(studentId, offeringId);
    }


    public Enrollment setMark(Long enrollmentId,  Integer mark) {
        return enrollmentService.setMark(enrollmentId, mark);
    }

    public List<Enrollment> allEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Student studentReport( Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }

/*
    public List<CourseOffering> coursesByTeacher( Long teacherId,
                                                  Integer year,
                                                  SemesterEnum semester) {
        if (year != null && semester != null) {
            return offeringRepository.findByTeacherIdAndSemesterAndYear(teacherId, semester, year);
        } else if (year != null) {
            return offeringRepository.findByTeacherIdAndYear(teacherId, year);
        } else {
            return offeringRepository.findByTeacherId(teacherId);
        }
    }
*/
}