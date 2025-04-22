package Simple.University.System.demo.Repository;

import Simple.University.System.demo.Entity.Course;
import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Repository.Core.BaseRepository;

import java.util.List;

public interface CourseRepository extends BaseRepository<Course> {

    List<Course> findDistinctByOfferings_Teacher_IdAndOfferings_YearAndOfferings_Semester(Long teacherId, Integer year, SemesterEnum semester);

    List<Course> findDistinctByOfferings_Teacher_IdAndOfferings_Year(Long teacherId, Integer year);

    List<Course> findDistinctByOfferings_Teacher_Id(Long teacherId);
}
