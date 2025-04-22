package Simple.University.System.demo.Extra.DTO;

import Simple.University.System.demo.Extra.SemesterEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseOfferingResponseDTO {
    private Long offeringId;
    private String courseCode;
    private String teacherName;
    private SemesterEnum semester;
    private Integer year;
}
