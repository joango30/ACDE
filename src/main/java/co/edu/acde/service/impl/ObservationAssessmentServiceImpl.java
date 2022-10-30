package co.edu.acde.service.impl;

import co.edu.acde.domain.ObservationAssessment;
import co.edu.acde.repository.ObservationAssessmentRepository;
import co.edu.acde.service.ObservationAssessmentService;
import co.edu.acde.service.dto.ObservationAssessmentDTO;
import co.edu.acde.service.mapper.ObservationAssessmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ObservationAssessment}.
 */
@Service
@Transactional
public class ObservationAssessmentServiceImpl implements ObservationAssessmentService {

    private final Logger log = LoggerFactory.getLogger(ObservationAssessmentServiceImpl.class);

    private final ObservationAssessmentRepository observationAssessmentRepository;

    private final ObservationAssessmentMapper observationAssessmentMapper;

    public ObservationAssessmentServiceImpl(
        ObservationAssessmentRepository observationAssessmentRepository,
        ObservationAssessmentMapper observationAssessmentMapper
    ) {
        this.observationAssessmentRepository = observationAssessmentRepository;
        this.observationAssessmentMapper = observationAssessmentMapper;
    }

    @Override
    public ObservationAssessmentDTO save(ObservationAssessmentDTO observationAssessmentDTO) {
        log.debug("Request to save ObservationAssessment : {}", observationAssessmentDTO);
        ObservationAssessment observationAssessment = observationAssessmentMapper.toEntity(observationAssessmentDTO);
        observationAssessment = observationAssessmentRepository.save(observationAssessment);
        return observationAssessmentMapper.toDto(observationAssessment);
    }

    @Override
    public ObservationAssessmentDTO update(ObservationAssessmentDTO observationAssessmentDTO) {
        log.debug("Request to update ObservationAssessment : {}", observationAssessmentDTO);
        ObservationAssessment observationAssessment = observationAssessmentMapper.toEntity(observationAssessmentDTO);
        observationAssessment = observationAssessmentRepository.save(observationAssessment);
        return observationAssessmentMapper.toDto(observationAssessment);
    }

    @Override
    public Optional<ObservationAssessmentDTO> partialUpdate(ObservationAssessmentDTO observationAssessmentDTO) {
        log.debug("Request to partially update ObservationAssessment : {}", observationAssessmentDTO);

        return observationAssessmentRepository
            .findById(observationAssessmentDTO.getId())
            .map(existingObservationAssessment -> {
                observationAssessmentMapper.partialUpdate(existingObservationAssessment, observationAssessmentDTO);

                return existingObservationAssessment;
            })
            .map(observationAssessmentRepository::save)
            .map(observationAssessmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ObservationAssessmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ObservationAssessments");
        return observationAssessmentRepository.findAll(pageable).map(observationAssessmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ObservationAssessmentDTO> findOne(Long id) {
        log.debug("Request to get ObservationAssessment : {}", id);
        return observationAssessmentRepository.findById(id).map(observationAssessmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ObservationAssessment : {}", id);
        observationAssessmentRepository.deleteById(id);
    }
}
