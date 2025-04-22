package Simple.University.System.demo.ServiceImpl;

import Simple.University.System.demo.Entity.Teacher;
import Simple.University.System.demo.Repository.TeacherRepository;
import Simple.University.System.demo.Repository.TeacherRepository;
import Simple.University.System.demo.Service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Teacher addTeacher(String name, String email) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setEmail(email);
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Long id, String name, String email) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        if (name != null) teacher.setName(name);
        if (email != null) teacher.setEmail(email);
        return teacherRepository.save(teacher);
    }

    @Override
    public boolean deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) return false;
        teacherRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findEntityById(id);
    }
}
