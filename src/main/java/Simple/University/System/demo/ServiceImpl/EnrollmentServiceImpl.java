package Simple.University.System.demo.ServiceImpl;

import Simple.University.System.demo.Entity.CourseOffering;
import Simple.University.System.demo.Entity.Enrollment;
import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Exception.BusinessException;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Repository.CourseOfferingRepository;
import Simple.University.System.demo.Repository.EnrollmentRepository;
import Simple.University.System.demo.Repository.StudentRepository;
import Simple.University.System.demo.Service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseOfferingRepository offeringRepository;


    @Override
    public Enrollment enrollStudent(Long studentId, Long offeringId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        CourseOffering offering = offeringRepository.findById(offeringId)
                .orElseThrow(() -> new IllegalArgumentException("Course offering not found"));


        if (enrollmentRepository.existsByStudentAndOfferingAndMarkIsNullOrMarkIsGreaterThanEqual(student, offering, 60)) {
            throw new BusinessException("Student already enrolled in this offering");
        }

        long currentEnrollments = enrollmentRepository.countByStudentAndOffering_SemesterAndOffering_Year(
                student, offering.getSemester(), offering.getYear());
        if (currentEnrollments >= 6) {
            throw new BusinessException("Student cannot enroll in more than 6 courses per semester");
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

        @Override
        public List<Enrollment> getEnrollmentsByStudentAndSemesterAndYear(Long studentId, SemesterEnum semester, int year) {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new IllegalArgumentException("Student not found"));

            return enrollmentRepository.findByStudentAndOffering_SemesterAndOffering_Year(
                    student, semester, year);
        }

    @Override
    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }
}