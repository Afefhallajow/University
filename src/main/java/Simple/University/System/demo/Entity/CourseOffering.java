package Simple.University.System.demo.Entity;

import Simple.University.System.demo.Entity.Core.BaseEntity;
import Simple.University.System.demo.Extra.SemesterEnum;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "course_offerings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"course_id", "semester", "year"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CourseOffering extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SemesterEnum semester;

    @Column(nullable = false)
    private Integer year;

    @OneToMany(mappedBy = "offering", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enrollment> enrollments;
}