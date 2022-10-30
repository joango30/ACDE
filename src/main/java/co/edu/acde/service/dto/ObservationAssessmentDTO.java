package co.edu.acde.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.acde.domain.ObservationAssessment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObservationAssessmentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 250)
    private String observationGeneral;

    @NotNull
    @Size(max = 250)
    private String appropriationEvaluation;

    @NotNull
    @Size(max = 250)
    private String observationtraining;

    private AssessmentDTO assessment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservationGeneral() {
        return observationGeneral;
    }

    public void setObservationGeneral(String observationGeneral) {
        this.observationGeneral = observationGeneral;
    }

    public String getAppropriationEvaluation() {
        return appropriationEvaluation;
    }

    public void setAppropriationEvaluation(String appropriationEvaluation) {
        this.appropriationEvaluation = appropriationEvaluation;
    }

    public String getObservationtraining() {
        return observationtraining;
    }

    public void setObservationtraining(String observationtraining) {
        this.observationtraining = observationtraining;
    }

    public AssessmentDTO getAssessment() {
        return assessment;
    }

    public void setAssessment(AssessmentDTO assessment) {
        this.assessment = assessment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObservationAssessmentDTO)) {
            return false;
        }

        ObservationAssessmentDTO observationAssessmentDTO = (ObservationAssessmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, observationAssessmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObservationAssessmentDTO{" +
            "id=" + getId() +
            ", observationGeneral='" + getObservationGeneral() + "'" +
            ", appropriationEvaluation='" + getAppropriationEvaluation() + "'" +
            ", observationtraining='" + getObservationtraining() + "'" +
            ", assessment=" + getAssessment() +
            "}";
    }
}
