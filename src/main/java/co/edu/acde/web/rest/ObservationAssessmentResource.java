package co.edu.acde.web.rest;

import co.edu.acde.repository.ObservationAssessmentRepository;
import co.edu.acde.service.ObservationAssessmentService;
import co.edu.acde.service.dto.ObservationAssessmentDTO;
import co.edu.acde.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.acde.domain.ObservationAssessment}.
 */
@RestController
@RequestMapping("/api")
public class ObservationAssessmentResource {

    private final Logger log = LoggerFactory.getLogger(ObservationAssessmentResource.class);

    private static final String ENTITY_NAME = "observationAssessment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObservationAssessmentService observationAssessmentService;

    private final ObservationAssessmentRepository observationAssessmentRepository;

    public ObservationAssessmentResource(
        ObservationAssessmentService observationAssessmentService,
        ObservationAssessmentRepository observationAssessmentRepository
    ) {
        this.observationAssessmentService = observationAssessmentService;
        this.observationAssessmentRepository = observationAssessmentRepository;
    }

    /**
     * {@code POST  /observation-assessments} : Create a new observationAssessment.
     *
     * @param observationAssessmentDTO the observationAssessmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new observationAssessmentDTO, or with status {@code 400 (Bad Request)} if the observationAssessment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/observation-assessments")
    public ResponseEntity<ObservationAssessmentDTO> createObservationAssessment(
        @Valid @RequestBody ObservationAssessmentDTO observationAssessmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ObservationAssessment : {}", observationAssessmentDTO);
        if (observationAssessmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new observationAssessment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObservationAssessmentDTO result = observationAssessmentService.save(observationAssessmentDTO);
        return ResponseEntity
            .created(new URI("/api/observation-assessments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /observation-assessments/:id} : Updates an existing observationAssessment.
     *
     * @param id the id of the observationAssessmentDTO to save.
     * @param observationAssessmentDTO the observationAssessmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated observationAssessmentDTO,
     * or with status {@code 400 (Bad Request)} if the observationAssessmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the observationAssessmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/observation-assessments/{id}")
    public ResponseEntity<ObservationAssessmentDTO> updateObservationAssessment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ObservationAssessmentDTO observationAssessmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ObservationAssessment : {}, {}", id, observationAssessmentDTO);
        if (observationAssessmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, observationAssessmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!observationAssessmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ObservationAssessmentDTO result = observationAssessmentService.update(observationAssessmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, observationAssessmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /observation-assessments/:id} : Partial updates given fields of an existing observationAssessment, field will ignore if it is null
     *
     * @param id the id of the observationAssessmentDTO to save.
     * @param observationAssessmentDTO the observationAssessmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated observationAssessmentDTO,
     * or with status {@code 400 (Bad Request)} if the observationAssessmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the observationAssessmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the observationAssessmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/observation-assessments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ObservationAssessmentDTO> partialUpdateObservationAssessment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ObservationAssessmentDTO observationAssessmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ObservationAssessment partially : {}, {}", id, observationAssessmentDTO);
        if (observationAssessmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, observationAssessmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!observationAssessmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ObservationAssessmentDTO> result = observationAssessmentService.partialUpdate(observationAssessmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, observationAssessmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /observation-assessments} : get all the observationAssessments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of observationAssessments in body.
     */
    @GetMapping("/observation-assessments")
    public ResponseEntity<List<ObservationAssessmentDTO>> getAllObservationAssessments(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ObservationAssessments");
        Page<ObservationAssessmentDTO> page = observationAssessmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /observation-assessments/:id} : get the "id" observationAssessment.
     *
     * @param id the id of the observationAssessmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the observationAssessmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/observation-assessments/{id}")
    public ResponseEntity<ObservationAssessmentDTO> getObservationAssessment(@PathVariable Long id) {
        log.debug("REST request to get ObservationAssessment : {}", id);
        Optional<ObservationAssessmentDTO> observationAssessmentDTO = observationAssessmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(observationAssessmentDTO);
    }

    /**
     * {@code DELETE  /observation-assessments/:id} : delete the "id" observationAssessment.
     *
     * @param id the id of the observationAssessmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/observation-assessments/{id}")
    public ResponseEntity<Void> deleteObservationAssessment(@PathVariable Long id) {
        log.debug("REST request to delete ObservationAssessment : {}", id);
        observationAssessmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
