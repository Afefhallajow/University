package Simple.University.System.demo.Entity;

import Simple.University.System.demo.Entity.Core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "enrollments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"offering_id", "student_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Enrollment extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "offering_id", nullable = false)
    private CourseOffering offering;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Min(0) @Max(100)
    private Integer mark;
}
