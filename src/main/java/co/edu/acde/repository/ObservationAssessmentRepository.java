package co.edu.acde.repository;

import co.edu.acde.domain.ObservationAssessment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ObservationAssessment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObservationAssessmentRepository extends JpaRepository<ObservationAssessment, Long> {}
