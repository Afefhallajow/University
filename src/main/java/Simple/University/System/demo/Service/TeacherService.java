package Simple.University.System.demo.Service;

import Simple.University.System.demo.Entity.Teacher;
import Simple.University.System.demo.Extra.PageResponse.TeacherPageResponse;

import java.util.Map;

public interface TeacherService {
    Teacher addTeacher(String name, String email, Map<String, Object> extra);
    Teacher updateTeacher(Long id, String name, String email, Map<String, Object> extra);
    boolean deleteTeacher(Long id);
    TeacherPageResponse getAllTeachers(int page, int size);
    Teacher getTeacherById(Long id);
}
