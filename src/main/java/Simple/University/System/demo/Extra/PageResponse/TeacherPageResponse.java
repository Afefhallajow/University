package Simple.University.System.demo.Extra.PageResponse;

import Simple.University.System.demo.Entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherPageResponse {

    int totalPages;
    int totalElements;
    int number;
    List<Teacher> data;

    public static TeacherPageResponse fromPage(Page<Teacher> page) {
        return new TeacherPageResponse(
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.getNumber(),
                page.getContent()
        );
    }

}
