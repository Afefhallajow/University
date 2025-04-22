package Simple.University.System.demo.Extra.DTO;

import Simple.University.System.demo.Extra.SemesterEnum;
import lombok.Data;

@Data
public class CourseMarkDTo {
    Long id;
    String title;
    String code;
    SemesterEnum semester;
    Integer mark;

    public CourseMarkDTo() {
    }

    public CourseMarkDTo(Long id, String title, String code, SemesterEnum semester, Integer mark) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.semester = semester;
        this.mark = mark;
    }
}