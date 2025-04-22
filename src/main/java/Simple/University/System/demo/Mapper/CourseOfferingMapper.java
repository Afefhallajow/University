package Simple.University.System.demo.Mapper;


import Simple.University.System.demo.Entity.CourseOffering;
import Simple.University.System.demo.Extra.DTO.CourseOfferingResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseOfferingMapper {
    @Mapping(source = "id", target = "offeringId")
    @Mapping(source = "course.code", target = "courseCode")
    @Mapping(source = "teacher.name", target = "teacherName")
    CourseOfferingResponseDTO toResponseDto(CourseOffering offering);
}
