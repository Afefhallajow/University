package Simple.University.System.demo.ServiceImpl;

import Simple.University.System.demo.Entity.CourseOffering;
import Simple.University.System.demo.Entity.Enrollment;
import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Entity.Teacher;
import Simple.University.System.demo.Repository.CourseOfferingRepository;
import Simple.University.System.demo.Repository.EnrollmentRepository;
import Simple.University.System.demo.Repository.StudentRepository;
import Simple.University.System.demo.Repository.TeacherRepository;
import Simple.University.System.demo.Service.EnrollmentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@NoArgsConstructor(force = true) @AllArgsConstructor
class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseOfferingRepository offeringRepository;
    private final TeacherRepository teacherRepository;


    @Override
    public Enrollment enrollStudent(Long studentId, Long offeringId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        CourseOffering offering = offeringRepository.findById(offeringId)
                .orElseThrow(() -> new IllegalArgumentException("Course offering not found"));

        if (enrollmentRepository.existsByStudentAndOffering(student, offering)) {
            throw new IllegalStateException("Student already enrolled in this offering");
        }

        long currentEnrollments = enrollmentRepository.countByStudentAndOffering_SemesterAndOffering_Year(
                student, offering.getSemester(), offering.getYear());
        if (currentEnrollments >= 6) {
            throw new IllegalStateException("Student cannot enroll in more than 6 courses per semester");
        }

        Teacher teacher = offering.getTeacher();
        if (teacher != null) {
            long teacherLoad = offeringRepository.countByTeacherAndSemesterAndYear(
                    teacher, offering.getSemester(), offering.getYear());
            if (teacherLoad >= 4) {
                throw new IllegalStateException("Teacher cannot teach more than 4 courses per semester");
            }
        }

        boolean alreadyPassed = enrollmentRepository.existsByStudentAndOffering_CourseAndMarkGreaterThanEqual(
                student, offering.getCourse(), 60);
        if (alreadyPassed) {
            throw new IllegalStateException("Student has already passed this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .offering(offering)
                .build();

        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment setMark(Long enrollmentId, Integer mark) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
        if (mark < 0 || mark > 100) {
            throw new IllegalArgumentException("Mark must be between 0 and 100");
        }
        enrollment.setMark(mark);
        return enrollmentRepository.save(enrollment);
    }
}
