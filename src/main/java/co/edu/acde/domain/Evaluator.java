package co.edu.acde.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Evaluator.
 */
@Entity
@Table(name = "evaluator")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Evaluator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "phone_number", length = 50, nullable = false)
    private String phoneNumber;

    @NotNull
    @Size(max = 100)
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Size(max = 20)
    @Column(name = "address", length = 20)
    private String address;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "evaluator")
    @JsonIgnoreProperties(value = { "observationAssessments", "evaluator", "employee" }, allowSetters = true)
    private Set<Assessment> assessments = new HashSet<>();

    @OneToMany(mappedBy = "evaluator")
    @JsonIgnoreProperties(value = { "employees", "evaluator" }, allowSetters = true)
    private Set<Training> trainings = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "evaluators" }, allowSetters = true)
    private DocumentType documentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Evaluator id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Evaluator phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public Evaluator email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public Evaluator address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Evaluator user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Assessment> getAssessments() {
        return this.assessments;
    }

    public void setAssessments(Set<Assessment> assessments) {
        if (this.assessments != null) {
            this.assessments.forEach(i -> i.setEvaluator(null));
        }
        if (assessments != null) {
            assessments.forEach(i -> i.setEvaluator(this));
        }
        this.assessments = assessments;
    }

    public Evaluator assessments(Set<Assessment> assessments) {
        this.setAssessments(assessments);
        return this;
    }

    public Evaluator addAssessment(Assessment assessment) {
        this.assessments.add(assessment);
        assessment.setEvaluator(this);
        return this;
    }

    public Evaluator removeAssessment(Assessment assessment) {
        this.assessments.remove(assessment);
        assessment.setEvaluator(null);
        return this;
    }

    public Set<Training> getTrainings() {
        return this.trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        if (this.trainings != null) {
            this.trainings.forEach(i -> i.setEvaluator(null));
        }
        if (trainings != null) {
            trainings.forEach(i -> i.setEvaluator(this));
        }
        this.trainings = trainings;
    }

    public Evaluator trainings(Set<Training> trainings) {
        this.setTrainings(trainings);
        return this;
    }

    public Evaluator addTraining(Training training) {
        this.trainings.add(training);
        training.setEvaluator(this);
        return this;
    }

    public Evaluator removeTraining(Training training) {
        this.trainings.remove(training);
        training.setEvaluator(null);
        return this;
    }

    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Evaluator documentType(DocumentType documentType) {
        this.setDocumentType(documentType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Evaluator)) {
            return false;
        }
        return id != null && id.equals(((Evaluator) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Evaluator{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
