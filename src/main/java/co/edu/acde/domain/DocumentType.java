package co.edu.acde.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DocumentType.
 */
@Entity
@Table(name = "document_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "initials", length = 10, nullable = false)
    private String initials;

    @NotNull
    @Size(max = 20)
    @Column(name = "document_name", length = 20, nullable = false)
    private String documentName;

    @OneToMany(mappedBy = "documentType")
    @JsonIgnoreProperties(value = { "user", "assessments", "trainings", "documentType" }, allowSetters = true)
    private Set<Evaluator> evaluators = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DocumentType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInitials() {
        return this.initials;
    }

    public DocumentType initials(String initials) {
        this.setInitials(initials);
        return this;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getDocumentName() {
        return this.documentName;
    }

    public DocumentType documentName(String documentName) {
        this.setDocumentName(documentName);
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Set<Evaluator> getEvaluators() {
        return this.evaluators;
    }

    public void setEvaluators(Set<Evaluator> evaluators) {
        if (this.evaluators != null) {
            this.evaluators.forEach(i -> i.setDocumentType(null));
        }
        if (evaluators != null) {
            evaluators.forEach(i -> i.setDocumentType(this));
        }
        this.evaluators = evaluators;
    }

    public DocumentType evaluators(Set<Evaluator> evaluators) {
        this.setEvaluators(evaluators);
        return this;
    }

    public DocumentType addEvaluator(Evaluator evaluator) {
        this.evaluators.add(evaluator);
        evaluator.setDocumentType(this);
        return this;
    }

    public DocumentType removeEvaluator(Evaluator evaluator) {
        this.evaluators.remove(evaluator);
        evaluator.setDocumentType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentType)) {
            return false;
        }
        return id != null && id.equals(((DocumentType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentType{" +
            "id=" + getId() +
            ", initials='" + getInitials() + "'" +
            ", documentName='" + getDocumentName() + "'" +
            "}";
    }
}
