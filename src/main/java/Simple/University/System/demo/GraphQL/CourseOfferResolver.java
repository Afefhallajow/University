package Simple.University.System.demo.GraphQL;

import Simple.University.System.demo.Extra.DTO.CourseOfferInput;
import Simple.University.System.demo.Extra.DTO.CourseOfferingResponseDTO;
import Simple.University.System.demo.Service.CourseOfferingService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class CourseOfferResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final CourseOfferingService courseOfferingService;

    public CourseOfferingResponseDTO addOffer(@Valid CourseOfferInput input) {
        return courseOfferingService.addOffer(input);
    }

    public CourseOfferingResponseDTO updateStudent(Long id, CourseOfferInput dto) {
        return courseOfferingService.updateOffer(id,dto);
    }
}
