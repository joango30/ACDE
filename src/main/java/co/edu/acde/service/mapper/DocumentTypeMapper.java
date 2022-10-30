package co.edu.acde.service.mapper;

import co.edu.acde.domain.DocumentType;
import co.edu.acde.service.dto.DocumentTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentType} and its DTO {@link DocumentTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentTypeMapper extends EntityMapper<DocumentTypeDTO, DocumentType> {}
