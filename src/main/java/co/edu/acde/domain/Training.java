package co.edu.acde.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Training.
 */
@Entity
@Table(name = "training")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Training implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "trainig_number", length = 100, nullable = false)
    private String trainigNumber;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Size(max = 100)
    @Column(name = "training_name", length = 100, nullable = false)
    private String trainingName;

    @NotNull
    @Size(max = 40)
    @Column(name = "status_name", length = 40, nullable = false)
    private String statusName;

    @OneToMany(mappedBy = "training")
    @JsonIgnoreProperties(value = { "user", "assessments", "training" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "assessments", "trainings", "documentType" }, allowSetters = true)
    private Evaluator evaluator;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Training id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainigNumber() {
        return this.trainigNumber;
    }

    public Training trainigNumber(String trainigNumber) {
        this.setTrainigNumber(trainigNumber);
        return this;
    }

    public void setTrainigNumber(String trainigNumber) {
        this.trainigNumber = trainigNumber;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Training startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Training endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTrainingName() {
        return this.trainingName;
    }

    public Training trainingName(String trainingName) {
        this.setTrainingName(trainingName);
        return this;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public Training statusName(String statusName) {
        this.setStatusName(statusName);
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setTraining(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setTraining(this));
        }
        this.employees = employees;
    }

    public Training employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Training addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setTraining(this);
        return this;
    }

    public Training removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setTraining(null);
        return this;
    }

    public Evaluator getEvaluator() {
        return this.evaluator;
    }

    public void setEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    public Training evaluator(Evaluator evaluator) {
        this.setEvaluator(evaluator);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Training)) {
            return false;
        }
        return id != null && id.equals(((Training) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Training{" +
            "id=" + getId() +
            ", trainigNumber='" + getTrainigNumber() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", trainingName='" + getTrainingName() + "'" +
            ", statusName='" + getStatusName() + "'" +
            "}";
    }
}
