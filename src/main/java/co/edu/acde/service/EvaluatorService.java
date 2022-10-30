package co.edu.acde.service;

import co.edu.acde.service.dto.EvaluatorDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.acde.domain.Evaluator}.
 */
public interface EvaluatorService {
    /**
     * Save a evaluator.
     *
     * @param evaluatorDTO the entity to save.
     * @return the persisted entity.
     */
    EvaluatorDTO save(EvaluatorDTO evaluatorDTO);

    /**
     * Updates a evaluator.
     *
     * @param evaluatorDTO the entity to update.
     * @return the persisted entity.
     */
    EvaluatorDTO update(EvaluatorDTO evaluatorDTO);

    /**
     * Partially updates a evaluator.
     *
     * @param evaluatorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EvaluatorDTO> partialUpdate(EvaluatorDTO evaluatorDTO);

    /**
     * Get all the evaluators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EvaluatorDTO> findAll(Pageable pageable);

    /**
     * Get all the evaluators with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EvaluatorDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" evaluator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvaluatorDTO> findOne(Long id);

    /**
     * Delete the "id" evaluator.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
