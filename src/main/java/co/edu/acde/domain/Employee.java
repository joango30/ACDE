package co.edu.acde.domain;

import co.edu.acde.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private State status;

    @NotNull
    @Size(max = 40)
    @Column(name = "charge", length = 40, nullable = false)
    private String charge;

    @NotNull
    @Size(max = 50)
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @NotNull
    @Size(max = 50)
    @Column(name = "phone_number", length = 50, nullable = false)
    private String phoneNumber;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "observationAssessments", "evaluator", "employee" }, allowSetters = true)
    private Set<Assessment> assessments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "employees", "evaluator" }, allowSetters = true)
    private Training training;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public State getStatus() {
        return this.status;
    }

    public Employee status(State status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(State status) {
        this.status = status;
    }

    public String getCharge() {
        return this.charge;
    }

    public Employee charge(String charge) {
        this.setCharge(charge);
        return this;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getEmail() {
        return this.email;
    }

    public Employee email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Employee phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Employee user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Assessment> getAssessments() {
        return this.assessments;
    }

    public void setAssessments(Set<Assessment> assessments) {
        if (this.assessments != null) {
            this.assessments.forEach(i -> i.setEmployee(null));
        }
        if (assessments != null) {
            assessments.forEach(i -> i.setEmployee(this));
        }
        this.assessments = assessments;
    }

    public Employee assessments(Set<Assessment> assessments) {
        this.setAssessments(assessments);
        return this;
    }

    public Employee addAssessment(Assessment assessment) {
        this.assessments.add(assessment);
        assessment.setEmployee(this);
        return this;
    }

    public Employee removeAssessment(Assessment assessment) {
        this.assessments.remove(assessment);
        assessment.setEmployee(null);
        return this;
    }

    public Training getTraining() {
        return this.training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Employee training(Training training) {
        this.setTraining(training);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", charge='" + getCharge() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
