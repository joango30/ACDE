package co.edu.acde.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ObservationAssessment.
 */
@Entity
@Table(name = "observation_assessment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObservationAssessment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "observation_general", length = 250, nullable = false)
    private String observationGeneral;

    @NotNull
    @Size(max = 250)
    @Column(name = "appropriation_evaluation", length = 250, nullable = false)
    private String appropriationEvaluation;

    @NotNull
    @Size(max = 250)
    @Column(name = "observationtraining", length = 250, nullable = false)
    private String observationtraining;

    @ManyToOne
    @JsonIgnoreProperties(value = { "observationAssessments", "evaluator", "employee" }, allowSetters = true)
    private Assessment assessment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ObservationAssessment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservationGeneral() {
        return this.observationGeneral;
    }

    public ObservationAssessment observationGeneral(String observationGeneral) {
        this.setObservationGeneral(observationGeneral);
        return this;
    }

    public void setObservationGeneral(String observationGeneral) {
        this.observationGeneral = observationGeneral;
    }

    public String getAppropriationEvaluation() {
        return this.appropriationEvaluation;
    }

    public ObservationAssessment appropriationEvaluation(String appropriationEvaluation) {
        this.setAppropriationEvaluation(appropriationEvaluation);
        return this;
    }

    public void setAppropriationEvaluation(String appropriationEvaluation) {
        this.appropriationEvaluation = appropriationEvaluation;
    }

    public String getObservationtraining() {
        return this.observationtraining;
    }

    public ObservationAssessment observationtraining(String observationtraining) {
        this.setObservationtraining(observationtraining);
        return this;
    }

    public void setObservationtraining(String observationtraining) {
        this.observationtraining = observationtraining;
    }

    public Assessment getAssessment() {
        return this.assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public ObservationAssessment assessment(Assessment assessment) {
        this.setAssessment(assessment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObservationAssessment)) {
            return false;
        }
        return id != null && id.equals(((ObservationAssessment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObservationAssessment{" +
            "id=" + getId() +
            ", observationGeneral='" + getObservationGeneral() + "'" +
            ", appropriationEvaluation='" + getAppropriationEvaluation() + "'" +
            ", observationtraining='" + getObservationtraining() + "'" +
            "}";
    }
}
