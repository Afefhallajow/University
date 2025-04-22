package Simple.University.System.demo.Service;

import Simple.University.System.demo.Extra.DTO.CourseOfferInput;
import Simple.University.System.demo.Extra.DTO.CourseOfferingResponseDTO;
import Simple.University.System.demo.Extra.SemesterEnum;

import java.util.List;

public interface CourseOfferingService {
    CourseOfferingResponseDTO addOffer(CourseOfferInput courseOfferDTO);
    CourseOfferingResponseDTO updateOffer(Long id, CourseOfferInput courseOfferDTO);

    CourseOfferingResponseDTO getById(Long id);

    Boolean deleteOffer(Long id);

    List<CourseOfferingResponseDTO> getAllOfferingsByYearAndSemester(Integer year, SemesterEnum semester);
}

