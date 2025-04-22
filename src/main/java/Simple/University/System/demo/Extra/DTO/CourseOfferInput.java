package Simple.University.System.demo.Extra.DTO;

import Simple.University.System.demo.Extra.SemesterEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseOfferInput {

    @NotNull
    Long courseId;

    @NotNull
    Long teacherId;

    @NotNull
    SemesterEnum semester;

    @Min(2023)
    int year;
}
