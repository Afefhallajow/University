package Simple.University.System.demo.Repository;

import Simple.University.System.demo.Entity.Course;
import Simple.University.System.demo.Entity.CourseOffering;
import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Entity.Teacher;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Repository.Core.BaseRepository;

import java.util.List;

public interface CourseOfferingRepository extends BaseRepository<CourseOffering> {

    long countByTeacherAndSemesterAndYear(Teacher teacher, SemesterEnum semester, Integer year);

    boolean existsByCourseAndSemesterAndYear(Course course, SemesterEnum semester, int year);
}
