package co.edu.acde.service.impl;

import co.edu.acde.domain.Training;
import co.edu.acde.repository.TrainingRepository;
import co.edu.acde.service.TrainingService;
import co.edu.acde.service.dto.TrainingDTO;
import co.edu.acde.service.mapper.TrainingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Training}.
 */
@Service
@Transactional
public class TrainingServiceImpl implements TrainingService {

    private final Logger log = LoggerFactory.getLogger(TrainingServiceImpl.class);

    private final TrainingRepository trainingRepository;

    private final TrainingMapper trainingMapper;

    public TrainingServiceImpl(TrainingRepository trainingRepository, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
    }

    @Override
    public TrainingDTO save(TrainingDTO trainingDTO) {
        log.debug("Request to save Training : {}", trainingDTO);
        Training training = trainingMapper.toEntity(trainingDTO);
        training = trainingRepository.save(training);
        return trainingMapper.toDto(training);
    }

    @Override
    public TrainingDTO update(TrainingDTO trainingDTO) {
        log.debug("Request to update Training : {}", trainingDTO);
        Training training = trainingMapper.toEntity(trainingDTO);
        training = trainingRepository.save(training);
        return trainingMapper.toDto(training);
    }

    @Override
    public Optional<TrainingDTO> partialUpdate(TrainingDTO trainingDTO) {
        log.debug("Request to partially update Training : {}", trainingDTO);

        return trainingRepository
            .findById(trainingDTO.getId())
            .map(existingTraining -> {
                trainingMapper.partialUpdate(existingTraining, trainingDTO);

                return existingTraining;
            })
            .map(trainingRepository::save)
            .map(trainingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrainingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trainings");
        return trainingRepository.findAll(pageable).map(trainingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainingDTO> findOne(Long id) {
        log.debug("Request to get Training : {}", id);
        return trainingRepository.findById(id).map(trainingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Training : {}", id);
        trainingRepository.deleteById(id);
    }
}
