package co.edu.acde.service;

import co.edu.acde.service.dto.AssessmentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.acde.domain.Assessment}.
 */
public interface AssessmentService {
    /**
     * Save a assessment.
     *
     * @param assessmentDTO the entity to save.
     * @return the persisted entity.
     */
    AssessmentDTO save(AssessmentDTO assessmentDTO);

    /**
     * Updates a assessment.
     *
     * @param assessmentDTO the entity to update.
     * @return the persisted entity.
     */
    AssessmentDTO update(AssessmentDTO assessmentDTO);

    /**
     * Partially updates a assessment.
     *
     * @param assessmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AssessmentDTO> partialUpdate(AssessmentDTO assessmentDTO);

    /**
     * Get all the assessments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssessmentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" assessment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssessmentDTO> findOne(Long id);

    /**
     * Delete the "id" assessment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
