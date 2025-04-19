package Simple.University.System.demo.Entity;

import Simple.University.System.demo.Entity.Core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.Set;

@Entity
@Table(name = "students")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student extends BaseEntity {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String extra;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enrollment> enrollments;

}