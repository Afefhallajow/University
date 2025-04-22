package Simple.University.System.demo.Repository;

import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.DTO.CourseMarkDTo;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Repository.Core.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends BaseRepository<Student> {

    @Query("select new Simple.University.System.demo.Extra.DTO.CourseMarkDTo (" +
            "c.id, " +
            "c.title , " +
            "c.code, " +
            "e.offering.semester, " +
            "e.mark) " +
            "from Enrollment e " +
            "inner join e.offering.course c " +
            "where e.student.id = ?1 and (e.mark is null or e.mark < 60)")
    List<CourseMarkDTo> findRemainingCoursesByStudentId(Long id);

    @Query("select avg(en.mark)" +
            "from Enrollment en " +
            "where en.student.id = ?1 and en.mark is not null and en.mark > 60 ")
    Double getGPA(Long id);

    @Query("select new Simple.University.System.demo.Extra.DTO.CourseMarkDTo (" +
            "c.id, " +
            "c.title, " +
            "c.code, " +
            "e.offering.semester, " +
            "e.mark ) " +
            "from Enrollment e " +
            "inner join e.offering.course c " +
            "where e.student.id = ?1 and e.mark is not null " )
    List<CourseMarkDTo> getStudentMarks(Long id);

    List<Student> findDistinctByEnrollments_Offering_Course_Id(Long id);
    List<Student> findDistinctByEnrollments_Offering_Course_IdAndEnrollments_Offering_Semester(Long id, SemesterEnum semesterEnum);
}
