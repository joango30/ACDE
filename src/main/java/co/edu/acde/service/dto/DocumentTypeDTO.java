package co.edu.acde.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.acde.domain.DocumentType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentTypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String initials;

    @NotNull
    @Size(max = 20)
    private String documentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentTypeDTO)) {
            return false;
        }

        DocumentTypeDTO documentTypeDTO = (DocumentTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, documentTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentTypeDTO{" +
            "id=" + getId() +
            ", initials='" + getInitials() + "'" +
            ", documentName='" + getDocumentName() + "'" +
            "}";
    }
}
