package Simple.University.System.demo.GraphQL;

import Simple.University.System.demo.Extra.DTO.CourseOfferInput;
import Simple.University.System.demo.Extra.DTO.CourseOfferingResponseDTO;
import Simple.University.System.demo.Extra.SemesterEnum;
import Simple.University.System.demo.Service.CourseOfferingService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseOfferResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final CourseOfferingService courseOfferingService;

    public List<CourseOfferingResponseDTO> allCourseOfferings(Integer year, SemesterEnum semester) {
        return courseOfferingService.getAllOfferingsByYearAndSemester(year, semester);
    }
    public CourseOfferingResponseDTO addOffer(@Valid CourseOfferInput input) {
        return courseOfferingService.addOffer(input);
    }

    public CourseOfferingResponseDTO updateOffer(Long id, @Valid CourseOfferInput dto) {
        return courseOfferingService.updateOffer(id,dto);
    }
    public CourseOfferingResponseDTO getOfferById(Long id) {
        return courseOfferingService.getById(id);
    }

    public Boolean deleteOffer(Long id) {
        return courseOfferingService.deleteOffer(id);
    }
}