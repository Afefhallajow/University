package Simple.University.System.demo.Repository;

import Simple.University.System.demo.Entity.CourseOffering;
import Simple.University.System.demo.Entity.Enrollment;
import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Repository.Core.BaseRepository;

import java.util.List;

public interface EnrollmentRepository extends BaseRepository<Enrollment> {


    boolean existsByStudentAndOfferingAndMarkIsNullOrMarkIsGreaterThanEqual(Student student, CourseOffering offering, Integer mark);

    long countByStudentAndOffering_SemesterAndOffering_Year(Student student, SemesterEnum semester, Integer year);

    List<Enrollment> findByStudentAndOffering_SemesterAndOffering_Year(Student student, SemesterEnum semester, int year);
}
