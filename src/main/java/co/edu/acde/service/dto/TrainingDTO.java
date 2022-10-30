package co.edu.acde.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.acde.domain.Training} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrainingDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String trainigNumber;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @Size(max = 100)
    private String trainingName;

    @NotNull
    @Size(max = 40)
    private String statusName;

    private EvaluatorDTO evaluator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainigNumber() {
        return trainigNumber;
    }

    public void setTrainigNumber(String trainigNumber) {
        this.trainigNumber = trainigNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public EvaluatorDTO getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(EvaluatorDTO evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrainingDTO)) {
            return false;
        }

        TrainingDTO trainingDTO = (TrainingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, trainingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainingDTO{" +
            "id=" + getId() +
            ", trainigNumber='" + getTrainigNumber() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", trainingName='" + getTrainingName() + "'" +
            ", statusName='" + getStatusName() + "'" +
            ", evaluator=" + getEvaluator() +
            "}";
    }
}
