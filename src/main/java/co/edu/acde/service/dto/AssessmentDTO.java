package co.edu.acde.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.acde.domain.Assessment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssessmentDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer assessmentNumber;

    @NotNull
    @Size(max = 50)
    private String assessmentType;

    @NotNull
    private LocalDate dateassessment;

    @NotNull
    private Integer assessmentTotal;

    private EvaluatorDTO evaluator;

    private EmployeeDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAssessmentNumber() {
        return assessmentNumber;
    }

    public void setAssessmentNumber(Integer assessmentNumber) {
        this.assessmentNumber = assessmentNumber;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public LocalDate getDateassessment() {
        return dateassessment;
    }

    public void setDateassessment(LocalDate dateassessment) {
        this.dateassessment = dateassessment;
    }

    public Integer getAssessmentTotal() {
        return assessmentTotal;
    }

    public void setAssessmentTotal(Integer assessmentTotal) {
        this.assessmentTotal = assessmentTotal;
    }

    public EvaluatorDTO getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(EvaluatorDTO evaluator) {
        this.evaluator = evaluator;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssessmentDTO)) {
            return false;
        }

        AssessmentDTO assessmentDTO = (AssessmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, assessmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssessmentDTO{" +
            "id=" + getId() +
            ", assessmentNumber=" + getAssessmentNumber() +
            ", assessmentType='" + getAssessmentType() + "'" +
            ", dateassessment='" + getDateassessment() + "'" +
            ", assessmentTotal=" + getAssessmentTotal() +
            ", evaluator=" + getEvaluator() +
            ", employee=" + getEmployee() +
            "}";
    }
}
