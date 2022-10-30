package co.edu.acde.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.acde.domain.Evaluator} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EvaluatorDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String phoneNumber;

    @NotNull
    @Size(max = 100)
    private String email;

    @Size(max = 20)
    private String address;

    private UserDTO user;

    private DocumentTypeDTO documentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public DocumentTypeDTO getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypeDTO documentType) {
        this.documentType = documentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluatorDTO)) {
            return false;
        }

        EvaluatorDTO evaluatorDTO = (EvaluatorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, evaluatorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluatorDTO{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", user=" + getUser() +
            ", documentType=" + getDocumentType() +
            "}";
    }
}
