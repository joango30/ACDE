package co.edu.acde.service.mapper;

import co.edu.acde.domain.DocumentType;
import co.edu.acde.domain.Evaluator;
import co.edu.acde.domain.User;
import co.edu.acde.service.dto.DocumentTypeDTO;
import co.edu.acde.service.dto.EvaluatorDTO;
import co.edu.acde.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Evaluator} and its DTO {@link EvaluatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface EvaluatorMapper extends EntityMapper<EvaluatorDTO, Evaluator> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "documentType", source = "documentType", qualifiedByName = "documentTypeDocumentName")
    EvaluatorDTO toDto(Evaluator s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("documentTypeDocumentName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "documentName", source = "documentName")
    DocumentTypeDTO toDtoDocumentTypeDocumentName(DocumentType documentType);
}
