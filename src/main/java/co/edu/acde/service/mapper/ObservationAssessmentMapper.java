package co.edu.acde.service.mapper;

import co.edu.acde.domain.Assessment;
import co.edu.acde.domain.ObservationAssessment;
import co.edu.acde.service.dto.AssessmentDTO;
import co.edu.acde.service.dto.ObservationAssessmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ObservationAssessment} and its DTO {@link ObservationAssessmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ObservationAssessmentMapper extends EntityMapper<ObservationAssessmentDTO, ObservationAssessment> {
    @Mapping(target = "assessment", source = "assessment", qualifiedByName = "assessmentId")
    ObservationAssessmentDTO toDto(ObservationAssessment s);

    @Named("assessmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AssessmentDTO toDtoAssessmentId(Assessment assessment);
}
