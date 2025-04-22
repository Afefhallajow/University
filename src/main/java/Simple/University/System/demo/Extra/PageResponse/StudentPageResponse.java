package Simple.University.System.demo.Extra.PageResponse;

import Simple.University.System.demo.Entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentPageResponse {

    int totalPages;
    int totalElements;
    int number;
    List<Student> data;

    public static StudentPageResponse fromPage(Page<Student> page) {
        return new StudentPageResponse(
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.getNumber(),
                page.getContent()
        );
    }

}
