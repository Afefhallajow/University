package Simple.University.System.demo.Extra.PageResponse;

import Simple.University.System.demo.Entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePageResponse {

    int totalPages;
    int totalElements;
    int number;
    List<Course> data;

    public static CoursePageResponse fromPage(Page<Course> page) {
        return new CoursePageResponse(
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.getNumber(),
                page.getContent()
        );
    }

}
