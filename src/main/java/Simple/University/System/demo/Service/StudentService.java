package Simple.University.System.demo.Service;

import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Extra.StudentReportResponse;

import java.util.List;

public interface StudentService {
    Student addStudent(String name, String email);
    Student updateStudent(Long id, String name, String email);
    boolean deleteStudent(Long id);
    List<Student> getAllStudents();

    Student getStudentById(Long id);

    StudentReportResponse getReport(Long id);

    List<Student> showStudentEnrollInCourseBySemester(Long id, SemesterEnum semester);
}
