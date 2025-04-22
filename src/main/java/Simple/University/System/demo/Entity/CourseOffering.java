package Simple.University.System.demo.Entity;

import Simple.University.System.demo.Config.PostgreSQLEnumType;
import Simple.University.System.demo.Entity.Core.BaseEntity;
import Simple.University.System.demo.Extra.SemesterEnum;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "course_offerings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"course_id", "semester", "year"}))
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CourseOffering extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    @Column(
            name = "semester",
            columnDefinition = "semester_enum",
            nullable = false)
    private SemesterEnum semester;

    @Column(nullable = false)
    private Integer year;

    @OneToMany(mappedBy = "offering", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enrollment> enrollments;
}