package Simple.University.System.demo.Service;

import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.PageResponse.StudentPageResponse;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Extra.StudentReportResponse;

import java.util.List;
import java.util.Map;

public interface StudentService {
    Student addStudent(String name, String email, Map<String, Object> extra);
    Student updateStudent(Long id, String name, String email, Map<String, Object> extra);
    boolean deleteStudent(Long id);
    StudentPageResponse getAllStudents(int page, int size);
    Student getStudentById(Long id);
    StudentReportResponse getReport(Long id);
    List<Student> showStudentEnrollInCourseBySemester(Long id, SemesterEnum semester);
}
