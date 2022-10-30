package co.edu.acde.service.mapper;

import co.edu.acde.domain.Assessment;
import co.edu.acde.domain.Employee;
import co.edu.acde.domain.Evaluator;
import co.edu.acde.service.dto.AssessmentDTO;
import co.edu.acde.service.dto.EmployeeDTO;
import co.edu.acde.service.dto.EvaluatorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Assessment} and its DTO {@link AssessmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssessmentMapper extends EntityMapper<AssessmentDTO, Assessment> {
    @Mapping(target = "evaluator", source = "evaluator", qualifiedByName = "evaluatorId")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    AssessmentDTO toDto(Assessment s);

    @Named("evaluatorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EvaluatorDTO toDtoEvaluatorId(Evaluator evaluator);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
