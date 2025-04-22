package Simple.University.System.demo.Extra.DTO;

import Simple.University.System.demo.Extra.SemesterEnum;
import lombok.Data;

@Data
public class CourseDto {
    Long id;
    String title;
    String code;
    SemesterEnum semester;

    public CourseDto() {
    }

    public CourseDto(Long id, String title, String code, SemesterEnum semester) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.semester = semester;
    }
}
