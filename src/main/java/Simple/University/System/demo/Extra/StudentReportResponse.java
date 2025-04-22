package Simple.University.System.demo.Extra;

import Simple.University.System.demo.Extra.DTO.CourseMarkDTo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentReportResponse {
    long id;
    String name;
    List<CourseMarkDTo> remainCourses;
    List<CourseMarkDTo> marks;
    Double gpa;
}
