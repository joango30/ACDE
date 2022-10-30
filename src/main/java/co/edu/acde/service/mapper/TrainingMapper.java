package co.edu.acde.service.mapper;

import co.edu.acde.domain.Evaluator;
import co.edu.acde.domain.Training;
import co.edu.acde.service.dto.EvaluatorDTO;
import co.edu.acde.service.dto.TrainingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Training} and its DTO {@link TrainingDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrainingMapper extends EntityMapper<TrainingDTO, Training> {
    @Mapping(target = "evaluator", source = "evaluator", qualifiedByName = "evaluatorId")
    TrainingDTO toDto(Training s);

    @Named("evaluatorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EvaluatorDTO toDtoEvaluatorId(Evaluator evaluator);
}
