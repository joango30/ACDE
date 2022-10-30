package co.edu.acde.service.mapper;

import co.edu.acde.domain.Employee;
import co.edu.acde.domain.Training;
import co.edu.acde.domain.User;
import co.edu.acde.service.dto.EmployeeDTO;
import co.edu.acde.service.dto.TrainingDTO;
import co.edu.acde.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "training", source = "training", qualifiedByName = "trainingId")
    EmployeeDTO toDto(Employee s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("trainingId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TrainingDTO toDtoTrainingId(Training training);
}
