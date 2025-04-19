package Simple.University.System.demo.Repository;

import Simple.University.System.demo.Entity.Course;
import Simple.University.System.demo.Entity.CourseOffering;
import Simple.University.System.demo.Entity.Enrollment;
import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Repository.Core.BaseRepository;

public interface EnrollmentRepository extends BaseRepository<Enrollment> {

    boolean existsByStudentAndOffering(Student student, CourseOffering offering);

    long countByStudentAndOffering_SemesterAndOffering_Year(Student student, SemesterEnum semester, Integer year);

    boolean existsByStudentAndOffering_CourseAndMarkGreaterThanEqual(Student student, Course course, int mark);
}
