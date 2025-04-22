package Simple.University.System.demo.ServiceImpl;

import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Extra.StudentReportResponse;
import Simple.University.System.demo.Repository.CourseRepository;
import Simple.University.System.demo.Repository.StudentRepository;
import Simple.University.System.demo.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    @Override
    public Student addStudent(String name, String email) {
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, String name, String email) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        if (name != null) student.setName(name);
        if (email != null) student.setEmail(email);
        return studentRepository.save(student);
    }

    @Override
    public boolean deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) return false;
        studentRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findEntityById(id);
    }

    @Override
    public StudentReportResponse getReport(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user is not found"));
        StudentReportResponse response = new StudentReportResponse(
                student.getId(),
                student.getName(),
                studentRepository.findRemainingCoursesByStudentId(id),
                studentRepository.getStudentMarks(id),
                studentRepository.getGPA(id));
        return response;
    }

    @Override
    public List<Student> showStudentEnrollInCourseBySemester(Long courseId, SemesterEnum semester) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found id=" + courseId));

        if (semester != null) {
            return studentRepository
                    .findDistinctByEnrollments_Offering_Course_IdAndEnrollments_Offering_Semester(
                            courseId, semester);
        } else {
            return studentRepository.findDistinctByEnrollments_Offering_Course_Id(courseId);
        }
    }
}