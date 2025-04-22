package Simple.University.System.demo.Service;

import Simple.University.System.demo.Entity.Enrollment;
import Simple.University.System.demo.Extra.SemesterEnum;

import java.util.List;

public interface EnrollmentService {
    Enrollment enrollStudent(Long studentId, Long offeringId);
    Enrollment setMark(Long enrollmentId, Integer mark);

    List<Enrollment> getEnrollmentsByStudentAndSemesterAndYear(Long studentId, SemesterEnum semester, int year);

    List<Enrollment> findAll();
}
