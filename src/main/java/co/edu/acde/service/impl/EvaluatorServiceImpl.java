package co.edu.acde.service.impl;

import co.edu.acde.domain.Evaluator;
import co.edu.acde.repository.EvaluatorRepository;
import co.edu.acde.service.EvaluatorService;
import co.edu.acde.service.dto.EvaluatorDTO;
import co.edu.acde.service.mapper.EvaluatorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Evaluator}.
 */
@Service
@Transactional
public class EvaluatorServiceImpl implements EvaluatorService {

    private final Logger log = LoggerFactory.getLogger(EvaluatorServiceImpl.class);

    private final EvaluatorRepository evaluatorRepository;

    private final EvaluatorMapper evaluatorMapper;

    public EvaluatorServiceImpl(EvaluatorRepository evaluatorRepository, EvaluatorMapper evaluatorMapper) {
        this.evaluatorRepository = evaluatorRepository;
        this.evaluatorMapper = evaluatorMapper;
    }

    @Override
    public EvaluatorDTO save(EvaluatorDTO evaluatorDTO) {
        log.debug("Request to save Evaluator : {}", evaluatorDTO);
        Evaluator evaluator = evaluatorMapper.toEntity(evaluatorDTO);
        evaluator = evaluatorRepository.save(evaluator);
        return evaluatorMapper.toDto(evaluator);
    }

    @Override
    public EvaluatorDTO update(EvaluatorDTO evaluatorDTO) {
        log.debug("Request to update Evaluator : {}", evaluatorDTO);
        Evaluator evaluator = evaluatorMapper.toEntity(evaluatorDTO);
        evaluator = evaluatorRepository.save(evaluator);
        return evaluatorMapper.toDto(evaluator);
    }

    @Override
    public Optional<EvaluatorDTO> partialUpdate(EvaluatorDTO evaluatorDTO) {
        log.debug("Request to partially update Evaluator : {}", evaluatorDTO);

        return evaluatorRepository
            .findById(evaluatorDTO.getId())
            .map(existingEvaluator -> {
                evaluatorMapper.partialUpdate(existingEvaluator, evaluatorDTO);

                return existingEvaluator;
            })
            .map(evaluatorRepository::save)
            .map(evaluatorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluatorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Evaluators");
        return evaluatorRepository.findAll(pageable).map(evaluatorMapper::toDto);
    }

    public Page<EvaluatorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return evaluatorRepository.findAllWithEagerRelationships(pageable).map(evaluatorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluatorDTO> findOne(Long id) {
        log.debug("Request to get Evaluator : {}", id);
        return evaluatorRepository.findOneWithEagerRelationships(id).map(evaluatorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Evaluator : {}", id);
        evaluatorRepository.deleteById(id);
    }
}
