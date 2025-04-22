package Simple.University.System.demo.Repository;

import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.DTO.CourseDto;
import Simple.University.System.demo.Extra.DTO.CourseMarkDTo;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Extra.StudentResponse;
import Simple.University.System.demo.Repository.Core.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends BaseRepository<Student> {

    @Query("""
    select new Simple.University.System.demo.Extra.StudentResponse(
      s.id,
      s.name,
      avg(en.mark))
    from Student s
    left join s.enrollments en
      on en.mark is not null
    where s.id = :sid
    group by s.id, s.name
    """)
    StudentResponse getReport1(@Param("sid") Long sid);

    @Query("select new Simple.University.System.demo.Extra.DTO.CourseDto (" +
            "c.id, " +
            "c.title , " +
            "c.code, " +
            "e.offering.semester) " +
            "from Enrollment e " +
            "inner join e.offering.course c " +
            "where e.student.id = ?1 and e.mark is  null ")
    List<CourseDto> findRemainingCoursesByStudentId(Long id);

    @Query("select avg(en.mark)/count(en) " +
            "from Enrollment en " +
            "where en.student.id = ?1 and en.mark is not null ")
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

    /*
            "(select new map " +
            "(e.offering.course.code as code,e.mark as mark, e.offering.semester as semster)  from Enrollment e " +
            " ) as marks) " +
*/
/*
            "(select avg(en.mark)/count(en)  " +
                    "from Enrollment en " +
                    "where en.student.id = ?1 and en.mark is not null) as gpa, " +
                    "(select f.id, f.offering.course.code, f.offering.semester from Enrollment f where f.student.id = ?1 and f.mark is null) " +
*/

}
