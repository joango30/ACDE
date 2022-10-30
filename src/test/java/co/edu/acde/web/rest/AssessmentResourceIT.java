package co.edu.acde.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.acde.IntegrationTest;
import co.edu.acde.domain.Assessment;
import co.edu.acde.repository.AssessmentRepository;
import co.edu.acde.service.dto.AssessmentDTO;
import co.edu.acde.service.mapper.AssessmentMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link AssessmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssessmentResourceIT {

    private static final Integer DEFAULT_ASSESSMENT_NUMBER = 1;
    private static final Integer UPDATED_ASSESSMENT_NUMBER = 2;

    private static final String DEFAULT_ASSESSMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ASSESSMENT_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATEASSESSMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEASSESSMENT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ASSESSMENT_TOTAL = 1;
    private static final Integer UPDATED_ASSESSMENT_TOTAL = 2;

    private static final String ENTITY_API_URL = "/api/assessments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private AssessmentMapper assessmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssessmentMockMvc;

    private Assessment assessment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assessment createEntity(EntityManager em) {
        Assessment assessment = new Assessment()
            .assessmentNumber(DEFAULT_ASSESSMENT_NUMBER)
            .assessmentType(DEFAULT_ASSESSMENT_TYPE)
            .dateassessment(DEFAULT_DATEASSESSMENT)
            .assessmentTotal(DEFAULT_ASSESSMENT_TOTAL);
        return assessment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assessment createUpdatedEntity(EntityManager em) {
        Assessment assessment = new Assessment()
            .assessmentNumber(UPDATED_ASSESSMENT_NUMBER)
            .assessmentType(UPDATED_ASSESSMENT_TYPE)
            .dateassessment(UPDATED_DATEASSESSMENT)
            .assessmentTotal(UPDATED_ASSESSMENT_TOTAL);
        return assessment;
    }

    @BeforeEach
    public void initTest() {
        assessment = createEntity(em);
    }

    @Test
    @Transactional
    void createAssessment() throws Exception {
        int databaseSizeBeforeCreate = assessmentRepository.findAll().size();
        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);
        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeCreate + 1);
        Assessment testAssessment = assessmentList.get(assessmentList.size() - 1);
        assertThat(testAssessment.getAssessmentNumber()).isEqualTo(DEFAULT_ASSESSMENT_NUMBER);
        assertThat(testAssessment.getAssessmentType()).isEqualTo(DEFAULT_ASSESSMENT_TYPE);
        assertThat(testAssessment.getDateassessment()).isEqualTo(DEFAULT_DATEASSESSMENT);
        assertThat(testAssessment.getAssessmentTotal()).isEqualTo(DEFAULT_ASSESSMENT_TOTAL);
    }

    @Test
    @Transactional
    void createAssessmentWithExistingId() throws Exception {
        // Create the Assessment with an existing ID
        assessment.setId(1L);
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        int databaseSizeBeforeCreate = assessmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAssessmentNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = assessmentRepository.findAll().size();
        // set the field null
        assessment.setAssessmentNumber(null);

        // Create the Assessment, which fails.
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssessmentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = assessmentRepository.findAll().size();
        // set the field null
        assessment.setAssessmentType(null);

        // Create the Assessment, which fails.
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateassessmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = assessmentRepository.findAll().size();
        // set the field null
        assessment.setDateassessment(null);

        // Create the Assessment, which fails.
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssessmentTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = assessmentRepository.findAll().size();
        // set the field null
        assessment.setAssessmentTotal(null);

        // Create the Assessment, which fails.
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAssessments() throws Exception {
        // Initialize the database
        assessmentRepository.saveAndFlush(assessment);

        // Get all the assessmentList
        restAssessmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assessment.getId().intValue())))
            .andExpect(jsonPath("$.[*].assessmentNumber").value(hasItem(DEFAULT_ASSESSMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].assessmentType").value(hasItem(DEFAULT_ASSESSMENT_TYPE)))
            .andExpect(jsonPath("$.[*].dateassessment").value(hasItem(DEFAULT_DATEASSESSMENT.toString())))
            .andExpect(jsonPath("$.[*].assessmentTotal").value(hasItem(DEFAULT_ASSESSMENT_TOTAL)));
    }

    @Test
    @Transactional
    void getAssessment() throws Exception {
        // Initialize the database
        assessmentRepository.saveAndFlush(assessment);

        // Get the assessment
        restAssessmentMockMvc
            .perform(get(ENTITY_API_URL_ID, assessment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assessment.getId().intValue()))
            .andExpect(jsonPath("$.assessmentNumber").value(DEFAULT_ASSESSMENT_NUMBER))
            .andExpect(jsonPath("$.assessmentType").value(DEFAULT_ASSESSMENT_TYPE))
            .andExpect(jsonPath("$.dateassessment").value(DEFAULT_DATEASSESSMENT.toString()))
            .andExpect(jsonPath("$.assessmentTotal").value(DEFAULT_ASSESSMENT_TOTAL));
    }

    @Test
    @Transactional
    void getNonExistingAssessment() throws Exception {
        // Get the assessment
        restAssessmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAssessment() throws Exception {
        // Initialize the database
        assessmentRepository.saveAndFlush(assessment);

        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();

        // Update the assessment
        Assessment updatedAssessment = assessmentRepository.findById(assessment.getId()).get();
        // Disconnect from session so that the updates on updatedAssessment are not directly saved in db
        em.detach(updatedAssessment);
        updatedAssessment
            .assessmentNumber(UPDATED_ASSESSMENT_NUMBER)
            .assessmentType(UPDATED_ASSESSMENT_TYPE)
            .dateassessment(UPDATED_DATEASSESSMENT)
            .assessmentTotal(UPDATED_ASSESSMENT_TOTAL);
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(updatedAssessment);

        restAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assessmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assessmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
        Assessment testAssessment = assessmentList.get(assessmentList.size() - 1);
        assertThat(testAssessment.getAssessmentNumber()).isEqualTo(UPDATED_ASSESSMENT_NUMBER);
        assertThat(testAssessment.getAssessmentType()).isEqualTo(UPDATED_ASSESSMENT_TYPE);
        assertThat(testAssessment.getDateassessment()).isEqualTo(UPDATED_DATEASSESSMENT);
        assertThat(testAssessment.getAssessmentTotal()).isEqualTo(UPDATED_ASSESSMENT_TOTAL);
    }

    @Test
    @Transactional
    void putNonExistingAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();
        assessment.setId(count.incrementAndGet());

        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assessmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();
        assessment.setId(count.incrementAndGet());

        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();
        assessment.setId(count.incrementAndGet());

        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssessmentWithPatch() throws Exception {
        // Initialize the database
        assessmentRepository.saveAndFlush(assessment);

        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();

        // Update the assessment using partial update
        Assessment partialUpdatedAssessment = new Assessment();
        partialUpdatedAssessment.setId(assessment.getId());

        partialUpdatedAssessment
            .assessmentNumber(UPDATED_ASSESSMENT_NUMBER)
            .assessmentType(UPDATED_ASSESSMENT_TYPE)
            .dateassessment(UPDATED_DATEASSESSMENT);

        restAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssessment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssessment))
            )
            .andExpect(status().isOk());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
        Assessment testAssessment = assessmentList.get(assessmentList.size() - 1);
        assertThat(testAssessment.getAssessmentNumber()).isEqualTo(UPDATED_ASSESSMENT_NUMBER);
        assertThat(testAssessment.getAssessmentType()).isEqualTo(UPDATED_ASSESSMENT_TYPE);
        assertThat(testAssessment.getDateassessment()).isEqualTo(UPDATED_DATEASSESSMENT);
        assertThat(testAssessment.getAssessmentTotal()).isEqualTo(DEFAULT_ASSESSMENT_TOTAL);
    }

    @Test
    @Transactional
    void fullUpdateAssessmentWithPatch() throws Exception {
        // Initialize the database
        assessmentRepository.saveAndFlush(assessment);

        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();

        // Update the assessment using partial update
        Assessment partialUpdatedAssessment = new Assessment();
        partialUpdatedAssessment.setId(assessment.getId());

        partialUpdatedAssessment
            .assessmentNumber(UPDATED_ASSESSMENT_NUMBER)
            .assessmentType(UPDATED_ASSESSMENT_TYPE)
            .dateassessment(UPDATED_DATEASSESSMENT)
            .assessmentTotal(UPDATED_ASSESSMENT_TOTAL);

        restAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssessment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssessment))
            )
            .andExpect(status().isOk());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
        Assessment testAssessment = assessmentList.get(assessmentList.size() - 1);
        assertThat(testAssessment.getAssessmentNumber()).isEqualTo(UPDATED_ASSESSMENT_NUMBER);
        assertThat(testAssessment.getAssessmentType()).isEqualTo(UPDATED_ASSESSMENT_TYPE);
        assertThat(testAssessment.getDateassessment()).isEqualTo(UPDATED_DATEASSESSMENT);
        assertThat(testAssessment.getAssessmentTotal()).isEqualTo(UPDATED_ASSESSMENT_TOTAL);
    }

    @Test
    @Transactional
    void patchNonExistingAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();
        assessment.setId(count.incrementAndGet());

        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assessmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();
        assessment.setId(count.incrementAndGet());

        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();
        assessment.setId(count.incrementAndGet());

        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(assessmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssessment() throws Exception {
        // Initialize the database
        assessmentRepository.saveAndFlush(assessment);

        int databaseSizeBeforeDelete = assessmentRepository.findAll().size();

        // Delete the assessment
        restAssessmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, assessment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
