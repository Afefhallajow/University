package Simple.University.System.demo.ServiceImpl;

import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.PageResponse.StudentPageResponse;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Extra.StudentReportResponse;
import Simple.University.System.demo.Repository.CourseRepository;
import Simple.University.System.demo.Repository.StudentRepository;
import Simple.University.System.demo.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private Student findStudentOrThrow(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id = " + id));
    }

    @Override
    public Student addStudent(String name, String email, Map<String, Object> extra) {
        Student student = Student.builder()
                .email(email)
                .name(name)
                .extra(extra)
                .build();
        student = studentRepository.save(student);
        logger.info("Student added successfully with id: {}", student.getId());
        return student;
    }

    @Override
    public Student updateStudent(Long id, String name, String email, Map<String, Object> extra) {
        Student student = findStudentOrThrow(id);

        if (name != null && !name.trim().isEmpty()) student.setName(name);
        if (email != null && !email.trim().isEmpty()) student.setEmail(email);
        if (extra != null && !extra.isEmpty()) student.setExtra(extra);


        student = studentRepository.save(student);
        logger.info("Student updated successfully with id: {}", student.getId());

        return student;
    }

    @Override
    public boolean deleteStudent(Long id) {
        Student student = findStudentOrThrow(id);
        studentRepository.delete(student);

        logger.info("Student deleted successfully with id: {}", id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public StudentPageResponse getAllStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return StudentPageResponse.fromPage(studentPage);
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return findStudentOrThrow(id);
    }

    //get detailed report about student [marks, GPA, remaining courses]
    @Override
    public StudentReportResponse getReport(Long id) {
        Student student = findStudentOrThrow(id);
        StudentReportResponse response = new StudentReportResponse(
                student.getId(),
                student.getName(),
                studentRepository.findRemainingCoursesByStudentId(id),
                studentRepository.getStudentMarks(id),
                studentRepository.getGPA(id));
        return response;
    }

    //show students enrolled in specific course in [specific semester, all semesters]
    @Override
    public List<Student> showStudentEnrollInCourseBySemester(Long courseId, SemesterEnum semester) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found id=" + courseId));

        return semester != null
                ? studentRepository.findDistinctByEnrollments_Offering_Course_IdAndEnrollments_Offering_Semester(courseId, semester)
                : studentRepository.findDistinctByEnrollments_Offering_Course_Id(courseId);
    }
}