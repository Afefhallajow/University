package Simple.University.System.demo.Service;

import Simple.University.System.demo.Entity.Enrollment;

public interface EnrollmentService {
    Enrollment enrollStudent(Long studentId, Long offeringId);
    Enrollment setMark(Long enrollmentId, Integer mark);

}
