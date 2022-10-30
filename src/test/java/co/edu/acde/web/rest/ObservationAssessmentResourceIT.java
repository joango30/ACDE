package co.edu.acde.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.acde.IntegrationTest;
import co.edu.acde.domain.ObservationAssessment;
import co.edu.acde.repository.ObservationAssessmentRepository;
import co.edu.acde.service.dto.ObservationAssessmentDTO;
import co.edu.acde.service.mapper.ObservationAssessmentMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ObservationAssessmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ObservationAssessmentResourceIT {

    private static final String DEFAULT_OBSERVATION_GENERAL = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION_GENERAL = "BBBBBBBBBB";

    private static final String DEFAULT_APPROPRIATION_EVALUATION = "AAAAAAAAAA";
    private static final String UPDATED_APPROPRIATION_EVALUATION = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVATIONTRAINING = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATIONTRAINING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/observation-assessments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObservationAssessmentRepository observationAssessmentRepository;

    @Autowired
    private ObservationAssessmentMapper observationAssessmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObservationAssessmentMockMvc;

    private ObservationAssessment observationAssessment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObservationAssessment createEntity(EntityManager em) {
        ObservationAssessment observationAssessment = new ObservationAssessment()
            .observationGeneral(DEFAULT_OBSERVATION_GENERAL)
            .appropriationEvaluation(DEFAULT_APPROPRIATION_EVALUATION)
            .observationtraining(DEFAULT_OBSERVATIONTRAINING);
        return observationAssessment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObservationAssessment createUpdatedEntity(EntityManager em) {
        ObservationAssessment observationAssessment = new ObservationAssessment()
            .observationGeneral(UPDATED_OBSERVATION_GENERAL)
            .appropriationEvaluation(UPDATED_APPROPRIATION_EVALUATION)
            .observationtraining(UPDATED_OBSERVATIONTRAINING);
        return observationAssessment;
    }

    @BeforeEach
    public void initTest() {
        observationAssessment = createEntity(em);
    }

    @Test
    @Transactional
    void createObservationAssessment() throws Exception {
        int databaseSizeBeforeCreate = observationAssessmentRepository.findAll().size();
        // Create the ObservationAssessment
        ObservationAssessmentDTO observationAssessmentDTO = observationAssessmentMapper.toDto(observationAssessment);
        restObservationAssessmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(observationAssessmentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ObservationAssessment in the database
        List<ObservationAssessment> observationAssessmentList = observationAssessmentRepository.findAll();
        assertThat(observationAssessmentList).hasSize(databaseSizeBeforeCreate + 1);
        ObservationAssessment testObservationAssessment = observationAssessmentList.get(observationAssessmentList.size() - 1);
        assertThat(testObservationAssessment.getObservationGeneral()).isEqualTo(DEFAULT_OBSERVATION_GENERAL);
        assertThat(testObservationAssessment.getAppropriationEvaluation()).isEqualTo(DEFAULT_APPROPRIATION_EVALUATION);
        assertThat(testObservationAssessment.getObservationtraining()).isEqualTo(DEFAULT_OBSERVATIONTRAINING);
    }

    @Test
    @Transactional
    void createObservationAssessmentWithExistingId() throws Exception {
        // Create the ObservationAssessment with an existing ID
        observationAssessment.setId(1L);
        ObservationAssessmentDTO observationAssessmentDTO = observationAssessmentMapper.toDto(observationAssessment);

        int databaseSizeBeforeCreate = observationAssessmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restObservationAssessmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(observationAssessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObservationAssessment in the database
        List<ObservationAssessment> observationAssessmentList = observationAssessmentRepository.findAll();
        assertThat(observationAssessmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkObservationGeneralIsRequired() throws Exception {
        int databaseSizeBeforeTest = observationAssessmentRepository.findAll().size();
        // set the field null
        observationAssessment.setObservationGeneral(null);

        // Create the ObservationAssessment, which fails.
        ObservationAssessmentDTO observationAssessmentDTO = observationAssessmentMapper.toDto(observationAssessment);

        restObservationAssessmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(observationAssessmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<ObservationAssessment> observationAssessmentList = observationAssessmentRepository.findAll();
        assertThat(observationAssessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAppropriationEvaluationIsRequired() throws Exception {
        int databaseSizeBeforeTest = observationAssessmentRepository.findAll().size();
        // set the field null
        observationAssessment.setAppropriationEvaluation(null);

        // Create the ObservationAssessment, which fails.
        ObservationAssessmentDTO observationAssessmentDTO = observationAssessmentMapper.toDto(observationAssessment);

        restObservationAssessmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(observationAssessmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<ObservationAssessment> observationAssessmentList = observationAssessmentRepository.findAll();
        assertThat(observationAssessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkObservationtrainingIsRequired() throws Exception {
        int databaseSizeBeforeTest = observationAssessmentRepository.findAll().size();
        // set the field null
        observationAssessment.setObservationtraining(null);

        // Create the ObservationAssessment, which fails.
        ObservationAssessmentDTO observationAssessmentDTO = observationAssessmentMapper.toDto(observationAssessment);

        restObservationAssessmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(observationAssessmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<ObservationAssessment> observationAssessmentList = observationAssessmentRepository.findAll();
        assertThat(observationAssessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllObservationAssessments() throws Exception {
        // Initialize the database
        observationAssessmentRepository.saveAndFlush(observationAssessment);

        // Get all the observationAssessmentList
        restObservationAssessmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observationAssessment.getId().intValue())))
            .andExpect(jsonPath("$.[*].observationGeneral").value(hasItem(DEFAULT_OBSERVATION_GENERAL)))
            .andExpect(jsonPath("$.[*].appropriationEvaluation").value(hasItem(DEFAULT_APPROPRIATION_EVALUATION)))
            .andExpect(jsonPath("$.[*].observationtraining").value(hasItem(DEFAULT_OBSERVATIONTRAINING)));
    }

    @Test
    @Transactional
    void getObservationAssessment() throws Exception {
        // Initialize the database
        observationAssessmentRepository.saveAndFlush(observationAssessment);

        // Get the observationAssessment
        restObservationAssessmentMockMvc
            .perform(get(ENTITY_API_URL_ID, observationAssessment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(observationAssessment.getId().intValue()))
            .andExpect(jsonPath("$.observationGeneral").value(DEFAULT_OBSERVATION_GENERAL))
            .andExpect(jsonPath("$.appropriationEvaluation").value(DEFAULT_APPROPRIATION_EVALUATION))
            .andExpect(jsonPath("$.observationtraining").value(DEFAULT_OBSERVATIONTRAINING));
    }

    @Test
    @Transactional
    void getNonExistingObservationAssessment() throws Exception {
        // Get the observationAssessment
        restObservationAssessmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingObservationAssessment() throws Exception {
        // Initialize the database
        observationAssessmentRepository.saveAndFlush(observationAssessment);

        int databaseSizeBeforeUpdate = observationAssessmentRepository.findAll().size();

        // Update the observationAssessment
        ObservationAssessment updatedObservationAssessment = observationAssessmentRepository.findById(observationAssessment.getId()).get();
        // Disconnect from session so that the updates on updatedObservationAssessment are not directly saved in db
        em.detach(updatedObservationAssessment);
        updatedObservationAssessment
            .observationGeneral(UPDATED_OBSERVATION_GENERAL)
            .appropriationEvaluation(UPDATED_APPROPRIATION_EVALUATION)
            .observationtraining(UPDATED_OBSERVATIONTRAINING);
        ObservationAssessmentDTO observationAssessmentDTO = observationAssessmentMapper.toDto(updatedObservationAssessment);

        restObservationAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, observationAssessmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(observationAssessmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the ObservationAssessment in the database
        List<ObservationAssessment> observationAssessmentList = observationAssessmentRepository.findAll();
        assertThat(observationAssessmentList).hasSize(databaseSizeBeforeUpdate);
        ObservationAssessment testObservationAssessment = observationAssessmentList.get(observationAssessmentList.size() - 1);
        assertThat(testObservationAssessment.getObservationGeneral()).isEqualTo(UPDATED_OBSERVATION_GENERAL);
        assertThat(testObservationAssessment.getAppropriationEvaluation()).isEqualTo(UPDATED_APPROPRIATION_EVALUATION);
        assertThat(testObservationAssessment.getObservationtraining()).isEqualTo(UPDATED_OBSERVATIONTRAINING);
    }

    @Test
    @Transactional
    void putNonExistingObservationAssessment() throws Exception {
        int databaseSizeBeforeUpdate = observationAssessmentRepository.findAll().size();
        observationAssessment.setId(count.incrementAndGet());

        // Create the ObservationAssessment
        ObservationAssessmentDTO observationAssessmentDTO = observationAssessmentMapper.toDto(observationAssessment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObservationAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, observationAssessmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(observationAssessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObservationAssessment in the database
        List<ObservationAssessment> observationAssessmentList = observationAssessmentRepository.findAll();
        assertThat(observationAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchObservationAssessment() throws Exception {
        int databaseSizeBeforeUpdate = observationAssessmentRepository.findAll().size();
        observationAssessment.setId(count.incrementAndGet());

        // Create the ObservationAssessment
        ObservationAssessmentDTO observationAssessmentDTO = observationAssessmentMapper.toDto(observationAssessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservationAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(observationAssessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObservationAssessment in the database
        List<ObservationAssessment> observationAssessmentList = observationAssessmentRepository.findAll();
        assertThat(observationAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamObservationAssessment() throws Exception {
        int databaseSizeBeforeUpdate = observationAssessmentRepository.findAll().size();
        observationAssessment.setId(count.incrementAndGet());

        // Create the ObservationAssessment
        ObservationAssessmentDTO observationAssessmentDTO = observationAssessmentMapper.toDto(observationAssessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservationAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(observationAssessmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ObservationAssessment in the database
        List<ObservationAssessment> observationAssessmentList = observationAssessmentRepository.findAll();
        assertThat(observationAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateObservationAssessmentWithPatch() throws Exception {
        // Initialize the database
        observationAssessmentRepository.saveAndFlush(observationAssessment);

        int databaseSizeBeforeUpdate = observationAssessmentRepository.findAll().size();

        // Update the observationAssessment using partial update
        ObservationAssessment partialUpdatedObservationAssessment = new ObservationAssessment();
        partialUpdatedObservationAssessment.setId(observationAssessment.getId());

        restObservationAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObservationAssessment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObservationAssessment))
            )
            .andExpect(status().isOk());

        // Validate the ObservationAssessment in the database
        List<ObservationAssessment> observationAssessmentList = observationAssessmentRepository.findAll();
        assertThat(observationAssessmentList).hasSize(databaseSizeBeforeUpdate);
        ObservationAssessment testObservationAssessment = observationAssessmentList.get(observationAssessmentList.size() - 1);
        assertThat(testObservationAssessment.getObservationGeneral()).isEqualTo(DEFAULT_OBSERVATION_GENERAL);
        assertThat(testObservationAssessment.getAppropriationEvaluation()).isEqualTo(DEFAULT_APPROPRIATION_EVALUATION);
        assertThat(testObservationAssessment.getObservationtraining()).isEqualTo(DEFAULT_OBSERVATIONTRAINING);
    }

    @Test
    @Transactional
    void fullUpdateObservationAssessmentWithPatch() throws Exception {
        // Initialize the database
        observationAssessmentRepository.saveAndFlush(observationAssessment);

        int databaseSizeBeforeUpdate = observationAssessmentRepository.findAll().size();

        // Update the observationAssessment using partial update
        ObservationAssessment partialUpdatedObservationAssessment = new ObservationAssessment();
        partialUpdatedObservationAssessment.setId(observationAssessment.getId());

        partialUpdatedObservationAssessment
            .observationGeneral(UPDATED_OBSERVATION_GENERAL)
            .appropriationEvaluation(UPDATED_APPROPRIATION_EVALUATION)
            .observationtraining(UPDATED_OBSERVATIONTRAINING);

        restObservationAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObservationAssessment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObservationAssessment))
            )
            .andExpect(status().isOk());

        // Validate the ObservationAssessment in the database
        List<ObservationAssessment> observationAssessmentList = observationAssessmentRepository.findAll();
        assertThat(observationAssessmentList).hasSize(databaseSizeBeforeUpdate);
        ObservationAssessment testObservationAssessment = observationAssessmentList.get(observationAssessmentList.size() - 1);
        assertThat(testObservationAssessment.getObservationGeneral()).isEqualTo(UPDATED_OBSERVATION_GENERAL);
        assertThat(testObservationAssessment.getAppropriationEvaluation()).isEqualTo(UPDATED_APPROPRIATION_EVALUATION);
        assertThat(testObservationAssessment.getObservationtraining()).isEqualTo(UPDATED_OBSERVATIONTRAINING);
    }

    @Test
    @Transactional
    void patchNonExistingObservationAssessment() throws Exception {
        int databaseSizeBeforeUpdate = observationAssessmentRepository.findAll().size();
        observationAssessment.setId(count.incrementAndGet());

        // Create the ObservationAssessment
        ObservationAssessmentDTO observationAssessmentDTO = observationAssessmentMapper.toDto(observationAssessment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObservationAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, observationAssessmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(observationAssessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObservationAssessment in the database
        List<ObservationAssessment> observationAssessmentList = observationAssessmentRepository.findAll();
        assertThat(observationAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchObservationAssessment() throws Exception {
        int databaseSizeBeforeUpdate = observationAssessmentRepository.findAll().size();
        observationAssessment.setId(count.incrementAndGet());

        // Create the ObservationAssessment
        ObservationAssessmentDTO observationAssessmentDTO = observationAssessmentMapper.toDto(observationAssessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservationAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(observationAssessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObservationAssessment in the database
        List<ObservationAssessment> observationAssessmentList = observationAssessmentRepository.findAll();
        assertThat(observationAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamObservationAssessment() throws Exception {
        int databaseSizeBeforeUpdate = observationAssessmentRepository.findAll().size();
        observationAssessment.setId(count.incrementAndGet());

        // Create the ObservationAssessment
        ObservationAssessmentDTO observationAssessmentDTO = observationAssessmentMapper.toDto(observationAssessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservationAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(observationAssessmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ObservationAssessment in the database
        List<ObservationAssessment> observationAssessmentList = observationAssessmentRepository.findAll();
        assertThat(observationAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteObservationAssessment() throws Exception {
        // Initialize the database
        observationAssessmentRepository.saveAndFlush(observationAssessment);

        int databaseSizeBeforeDelete = observationAssessmentRepository.findAll().size();

        // Delete the observationAssessment
        restObservationAssessmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, observationAssessment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ObservationAssessment> observationAssessmentList = observationAssessmentRepository.findAll();
        assertThat(observationAssessmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
