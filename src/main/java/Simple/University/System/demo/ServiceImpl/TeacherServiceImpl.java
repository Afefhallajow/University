package Simple.University.System.demo.ServiceImpl;

import Simple.University.System.demo.Entity.Teacher;
import Simple.University.System.demo.Extra.PageResponse.TeacherPageResponse;
import Simple.University.System.demo.Repository.TeacherRepository;
import Simple.University.System.demo.Service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {


    private final TeacherRepository teacherRepository;
    private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    private Teacher findTeacherOrThrow(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id = " + id));
    }

    @Override
    public Teacher addTeacher(String name, String email, Map<String, Object> extra) {
        Teacher teacher = Teacher.builder()
                .email(email)
                .name(name)
                .extra(extra)
                .build();
        teacher = teacherRepository.save(teacher);
        logger.info("Teacher added successfully with id: {}", teacher.getId());;
        return teacher;
    }

    @Override
    public Teacher updateTeacher(Long id, String name, String email, Map<String, Object> extra) {
        Teacher teacher = findTeacherOrThrow(id);
        if (name != null && !name.trim().isEmpty()) teacher.setName(name);
        if (email != null && !email.trim().isEmpty()) teacher.setEmail(email);
        Optional.ofNullable(extra).ifPresent(teacher::setExtra);

        teacher = teacherRepository.save(teacher);
        logger.info("Teacher updated successfully with id: {}", teacher.getId());
        return teacher;
    }

    @Override
    public boolean deleteTeacher(Long id) {
        Teacher teacher = findTeacherOrThrow(id);

        teacherRepository.delete(teacher);

        logger.info("Teacher deleted successfully with id: {}", id);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherPageResponse getAllTeachers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Teacher> teacherPage = teacherRepository.findAll(pageable);

        return TeacherPageResponse.fromPage(teacherPage);
    }

    @Override
    @Transactional(readOnly = true)
    public Teacher getTeacherById(Long id) {
        return findTeacherOrThrow(id);
    }
}