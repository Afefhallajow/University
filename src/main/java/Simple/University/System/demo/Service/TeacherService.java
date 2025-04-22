package Simple.University.System.demo.Service;

import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Entity.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher addTeacher(String name, String email);
    Teacher updateTeacher(Long id, String name, String email);
    boolean deleteTeacher(Long id);
    List<Teacher> getAllTeachers();
    Teacher getTeacherById(Long id);
}
