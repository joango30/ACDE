package co.edu.acde.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Assessment.
 */
@Entity
@Table(name = "assessment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Assessment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "assessment_number", nullable = false)
    private Integer assessmentNumber;

    @NotNull
    @Size(max = 50)
    @Column(name = "assessment_type", length = 50, nullable = false)
    private String assessmentType;

    @NotNull
    @Column(name = "dateassessment", nullable = false)
    private LocalDate dateassessment;

    @NotNull
    @Column(name = "assessment_total", nullable = false)
    private Integer assessmentTotal;

    @OneToMany(mappedBy = "assessment")
    @JsonIgnoreProperties(value = { "assessment" }, allowSetters = true)
    private Set<ObservationAssessment> observationAssessments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "assessments", "trainings", "documentType" }, allowSetters = true)
    private Evaluator evaluator;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "assessments", "training" }, allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Assessment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAssessmentNumber() {
        return this.assessmentNumber;
    }

    public Assessment assessmentNumber(Integer assessmentNumber) {
        this.setAssessmentNumber(assessmentNumber);
        return this;
    }

    public void setAssessmentNumber(Integer assessmentNumber) {
        this.assessmentNumber = assessmentNumber;
    }

    public String getAssessmentType() {
        return this.assessmentType;
    }

    public Assessment assessmentType(String assessmentType) {
        this.setAssessmentType(assessmentType);
        return this;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public LocalDate getDateassessment() {
        return this.dateassessment;
    }

    public Assessment dateassessment(LocalDate dateassessment) {
        this.setDateassessment(dateassessment);
        return this;
    }

    public void setDateassessment(LocalDate dateassessment) {
        this.dateassessment = dateassessment;
    }

    public Integer getAssessmentTotal() {
        return this.assessmentTotal;
    }

    public Assessment assessmentTotal(Integer assessmentTotal) {
        this.setAssessmentTotal(assessmentTotal);
        return this;
    }

    public void setAssessmentTotal(Integer assessmentTotal) {
        this.assessmentTotal = assessmentTotal;
    }

    public Set<ObservationAssessment> getObservationAssessments() {
        return this.observationAssessments;
    }

    public void setObservationAssessments(Set<ObservationAssessment> observationAssessments) {
        if (this.observationAssessments != null) {
            this.observationAssessments.forEach(i -> i.setAssessment(null));
        }
        if (observationAssessments != null) {
            observationAssessments.forEach(i -> i.setAssessment(this));
        }
        this.observationAssessments = observationAssessments;
    }

    public Assessment observationAssessments(Set<ObservationAssessment> observationAssessments) {
        this.setObservationAssessments(observationAssessments);
        return this;
    }

    public Assessment addObservationAssessment(ObservationAssessment observationAssessment) {
        this.observationAssessments.add(observationAssessment);
        observationAssessment.setAssessment(this);
        return this;
    }

    public Assessment removeObservationAssessment(ObservationAssessment observationAssessment) {
        this.observationAssessments.remove(observationAssessment);
        observationAssessment.setAssessment(null);
        return this;
    }

    public Evaluator getEvaluator() {
        return this.evaluator;
    }

    public void setEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    public Assessment evaluator(Evaluator evaluator) {
        this.setEvaluator(evaluator);
        return this;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Assessment employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Assessment)) {
            return false;
        }
        return id != null && id.equals(((Assessment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Assessment{" +
            "id=" + getId() +
            ", assessmentNumber=" + getAssessmentNumber() +
            ", assessmentType='" + getAssessmentType() + "'" +
            ", dateassessment='" + getDateassessment() + "'" +
            ", assessmentTotal=" + getAssessmentTotal() +
            "}";
    }
}
