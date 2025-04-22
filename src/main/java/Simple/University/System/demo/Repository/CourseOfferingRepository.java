package Simple.University.System.demo.Repository;

import Simple.University.System.demo.Entity.Course;
import Simple.University.System.demo.Entity.CourseOffering;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Repository.Core.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseOfferingRepository extends BaseRepository<CourseOffering> {

    @Query("select count(c.id) >= 4 from " +
            "CourseOffering c " +
            "where c.teacher.id = ?1 and c.year = ?2 and c.semester = ?3")
    Boolean existMoreThanFourByTeacher_IdAndYearAndSemester(
            Long teacherId,
            int year,
            SemesterEnum semester);

    boolean existsByCourseAndSemesterAndYear(Course course, SemesterEnum semester, int year);

    List<CourseOffering> findByYearAndSemester(Integer year, SemesterEnum semester);

    List<CourseOffering> findByYear(Integer year);
}
