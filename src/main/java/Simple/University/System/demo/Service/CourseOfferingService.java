package Simple.University.System.demo.Service;

import Simple.University.System.demo.Entity.Student;
import Simple.University.System.demo.Extra.DTO.CourseOfferInput;
import Simple.University.System.demo.Extra.DTO.CourseOfferingResponseDTO;
import Simple.University.System.demo.Extra.SemesterEnum;

import java.util.List;

public interface CourseOfferingService {
    CourseOfferingResponseDTO addOffer(CourseOfferInput courseOfferDTO);
    CourseOfferingResponseDTO updateOffer(Long id, CourseOfferInput courseOfferDTO);
 /*   boolean deleteOffer(Long id);
    List<CourseOfferingResponseDTO> getAllOffers();
    CourseOfferingResponseDTO getOfferById(Long id);
*/}

