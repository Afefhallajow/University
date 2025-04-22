package Simple.University.System.demo.Extra.DTO;

import Simple.University.System.demo.Extra.SemesterEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseOfferInput {
    @NotBlank
    Long courseId;
    @NotBlank
    Long teacherId;
    @NotBlank
    SemesterEnum semester;

    @NotBlank
    int year;
}
