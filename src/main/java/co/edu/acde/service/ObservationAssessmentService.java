package co.edu.acde.service;

import co.edu.acde.service.dto.ObservationAssessmentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.acde.domain.ObservationAssessment}.
 */
public interface ObservationAssessmentService {
    /**
     * Save a observationAssessment.
     *
     * @param observationAssessmentDTO the entity to save.
     * @return the persisted entity.
     */
    ObservationAssessmentDTO save(ObservationAssessmentDTO observationAssessmentDTO);

    /**
     * Updates a observationAssessment.
     *
     * @param observationAssessmentDTO the entity to update.
     * @return the persisted entity.
     */
    ObservationAssessmentDTO update(ObservationAssessmentDTO observationAssessmentDTO);

    /**
     * Partially updates a observationAssessment.
     *
     * @param observationAssessmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ObservationAssessmentDTO> partialUpdate(ObservationAssessmentDTO observationAssessmentDTO);

    /**
     * Get all the observationAssessments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ObservationAssessmentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" observationAssessment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ObservationAssessmentDTO> findOne(Long id);

    /**
     * Delete the "id" observationAssessment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
