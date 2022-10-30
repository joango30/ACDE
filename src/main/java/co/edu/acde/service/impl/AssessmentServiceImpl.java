package co.edu.acde.service.impl;

import co.edu.acde.domain.Assessment;
import co.edu.acde.repository.AssessmentRepository;
import co.edu.acde.service.AssessmentService;
import co.edu.acde.service.dto.AssessmentDTO;
import co.edu.acde.service.mapper.AssessmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Assessment}.
 */
@Service
@Transactional
public class AssessmentServiceImpl implements AssessmentService {

    private final Logger log = LoggerFactory.getLogger(AssessmentServiceImpl.class);

    private final AssessmentRepository assessmentRepository;

    private final AssessmentMapper assessmentMapper;

    public AssessmentServiceImpl(AssessmentRepository assessmentRepository, AssessmentMapper assessmentMapper) {
        this.assessmentRepository = assessmentRepository;
        this.assessmentMapper = assessmentMapper;
    }

    @Override
    public AssessmentDTO save(AssessmentDTO assessmentDTO) {
        log.debug("Request to save Assessment : {}", assessmentDTO);
        Assessment assessment = assessmentMapper.toEntity(assessmentDTO);
        assessment = assessmentRepository.save(assessment);
        return assessmentMapper.toDto(assessment);
    }

    @Override
    public AssessmentDTO update(AssessmentDTO assessmentDTO) {
        log.debug("Request to update Assessment : {}", assessmentDTO);
        Assessment assessment = assessmentMapper.toEntity(assessmentDTO);
        assessment = assessmentRepository.save(assessment);
        return assessmentMapper.toDto(assessment);
    }

    @Override
    public Optional<AssessmentDTO> partialUpdate(AssessmentDTO assessmentDTO) {
        log.debug("Request to partially update Assessment : {}", assessmentDTO);

        return assessmentRepository
            .findById(assessmentDTO.getId())
            .map(existingAssessment -> {
                assessmentMapper.partialUpdate(existingAssessment, assessmentDTO);

                return existingAssessment;
            })
            .map(assessmentRepository::save)
            .map(assessmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssessmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Assessments");
        return assessmentRepository.findAll(pageable).map(assessmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AssessmentDTO> findOne(Long id) {
        log.debug("Request to get Assessment : {}", id);
        return assessmentRepository.findById(id).map(assessmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Assessment : {}", id);
        assessmentRepository.deleteById(id);
    }
}
