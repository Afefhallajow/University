package Simple.University.System.demo.GraphQL;

import Simple.University.System.demo.Entity.CourseOffering;
import Simple.University.System.demo.Entity.Enrollment;
import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Repository.CourseOfferingRepository;
import Simple.University.System.demo.Repository.EnrollmentRepository;
import Simple.University.System.demo.Repository.StudentRepository;
import Simple.University.System.demo.Service.EnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class EnrollmentResolver {

    private final EnrollmentService enrollmentService;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseOfferingRepository offeringRepository;


    @MutationMapping
    public Enrollment enrollStudent(@Argument Long studentId, @Argument Long offeringId) {
        return enrollmentService.enrollStudent(studentId, offeringId);
    }

    @MutationMapping
    public Enrollment setMark(@Argument Long enrollmentId, @Argument Integer mark) {
        return enrollmentService.setMark(enrollmentId, mark);
    }

    @QueryMapping
    public List<Enrollment> allEnrollments() {
        return enrollmentRepository.findAll();
    }

    @QueryMapping
    public Student studentReport(@Argument Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }

    @QueryMapping
    public List<CourseOffering> coursesByTeacher(@Argument Long teacherId,
                                                 @Argument() Integer year,
                                                 @Argument() SemesterEnum semester) {
        if (year != null && semester != null) {
            return offeringRepository.findByTeacherIdAndSemesterAndYear(teacherId, semester, year);
        } else if (year != null) {
            return offeringRepository.findByTeacherIdAndYear(teacherId, year);
        } else {
            return offeringRepository.findByTeacherId(teacherId);
        }
    }
}