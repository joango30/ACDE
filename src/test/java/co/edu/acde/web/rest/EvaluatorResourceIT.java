package co.edu.acde.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.acde.IntegrationTest;
import co.edu.acde.domain.Evaluator;
import co.edu.acde.domain.User;
import co.edu.acde.repository.EvaluatorRepository;
import co.edu.acde.service.EvaluatorService;
import co.edu.acde.service.dto.EvaluatorDTO;
import co.edu.acde.service.mapper.EvaluatorMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EvaluatorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EvaluatorResourceIT {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/evaluators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EvaluatorRepository evaluatorRepository;

    @Mock
    private EvaluatorRepository evaluatorRepositoryMock;

    @Autowired
    private EvaluatorMapper evaluatorMapper;

    @Mock
    private EvaluatorService evaluatorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEvaluatorMockMvc;

    private Evaluator evaluator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evaluator createEntity(EntityManager em) {
        Evaluator evaluator = new Evaluator().phoneNumber(DEFAULT_PHONE_NUMBER).email(DEFAULT_EMAIL).address(DEFAULT_ADDRESS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        evaluator.setUser(user);
        return evaluator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evaluator createUpdatedEntity(EntityManager em) {
        Evaluator evaluator = new Evaluator().phoneNumber(UPDATED_PHONE_NUMBER).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        evaluator.setUser(user);
        return evaluator;
    }

    @BeforeEach
    public void initTest() {
        evaluator = createEntity(em);
    }

    @Test
    @Transactional
    void createEvaluator() throws Exception {
        int databaseSizeBeforeCreate = evaluatorRepository.findAll().size();
        // Create the Evaluator
        EvaluatorDTO evaluatorDTO = evaluatorMapper.toDto(evaluator);
        restEvaluatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluatorDTO)))
            .andExpect(status().isCreated());

        // Validate the Evaluator in the database
        List<Evaluator> evaluatorList = evaluatorRepository.findAll();
        assertThat(evaluatorList).hasSize(databaseSizeBeforeCreate + 1);
        Evaluator testEvaluator = evaluatorList.get(evaluatorList.size() - 1);
        assertThat(testEvaluator.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testEvaluator.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEvaluator.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    void createEvaluatorWithExistingId() throws Exception {
        // Create the Evaluator with an existing ID
        evaluator.setId(1L);
        EvaluatorDTO evaluatorDTO = evaluatorMapper.toDto(evaluator);

        int databaseSizeBeforeCreate = evaluatorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Evaluator in the database
        List<Evaluator> evaluatorList = evaluatorRepository.findAll();
        assertThat(evaluatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = evaluatorRepository.findAll().size();
        // set the field null
        evaluator.setPhoneNumber(null);

        // Create the Evaluator, which fails.
        EvaluatorDTO evaluatorDTO = evaluatorMapper.toDto(evaluator);

        restEvaluatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluatorDTO)))
            .andExpect(status().isBadRequest());

        List<Evaluator> evaluatorList = evaluatorRepository.findAll();
        assertThat(evaluatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = evaluatorRepository.findAll().size();
        // set the field null
        evaluator.setEmail(null);

        // Create the Evaluator, which fails.
        EvaluatorDTO evaluatorDTO = evaluatorMapper.toDto(evaluator);

        restEvaluatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluatorDTO)))
            .andExpect(status().isBadRequest());

        List<Evaluator> evaluatorList = evaluatorRepository.findAll();
        assertThat(evaluatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEvaluators() throws Exception {
        // Initialize the database
        evaluatorRepository.saveAndFlush(evaluator);

        // Get all the evaluatorList
        restEvaluatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluator.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEvaluatorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(evaluatorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEvaluatorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(evaluatorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEvaluatorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(evaluatorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEvaluatorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(evaluatorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEvaluator() throws Exception {
        // Initialize the database
        evaluatorRepository.saveAndFlush(evaluator);

        // Get the evaluator
        restEvaluatorMockMvc
            .perform(get(ENTITY_API_URL_ID, evaluator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evaluator.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    @Transactional
    void getNonExistingEvaluator() throws Exception {
        // Get the evaluator
        restEvaluatorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEvaluator() throws Exception {
        // Initialize the database
        evaluatorRepository.saveAndFlush(evaluator);

        int databaseSizeBeforeUpdate = evaluatorRepository.findAll().size();

        // Update the evaluator
        Evaluator updatedEvaluator = evaluatorRepository.findById(evaluator.getId()).get();
        // Disconnect from session so that the updates on updatedEvaluator are not directly saved in db
        em.detach(updatedEvaluator);
        updatedEvaluator.phoneNumber(UPDATED_PHONE_NUMBER).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);
        EvaluatorDTO evaluatorDTO = evaluatorMapper.toDto(updatedEvaluator);

        restEvaluatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluatorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Evaluator in the database
        List<Evaluator> evaluatorList = evaluatorRepository.findAll();
        assertThat(evaluatorList).hasSize(databaseSizeBeforeUpdate);
        Evaluator testEvaluator = evaluatorList.get(evaluatorList.size() - 1);
        assertThat(testEvaluator.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testEvaluator.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEvaluator.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void putNonExistingEvaluator() throws Exception {
        int databaseSizeBeforeUpdate = evaluatorRepository.findAll().size();
        evaluator.setId(count.incrementAndGet());

        // Create the Evaluator
        EvaluatorDTO evaluatorDTO = evaluatorMapper.toDto(evaluator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evaluator in the database
        List<Evaluator> evaluatorList = evaluatorRepository.findAll();
        assertThat(evaluatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvaluator() throws Exception {
        int databaseSizeBeforeUpdate = evaluatorRepository.findAll().size();
        evaluator.setId(count.incrementAndGet());

        // Create the Evaluator
        EvaluatorDTO evaluatorDTO = evaluatorMapper.toDto(evaluator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evaluator in the database
        List<Evaluator> evaluatorList = evaluatorRepository.findAll();
        assertThat(evaluatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvaluator() throws Exception {
        int databaseSizeBeforeUpdate = evaluatorRepository.findAll().size();
        evaluator.setId(count.incrementAndGet());

        // Create the Evaluator
        EvaluatorDTO evaluatorDTO = evaluatorMapper.toDto(evaluator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluatorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluatorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Evaluator in the database
        List<Evaluator> evaluatorList = evaluatorRepository.findAll();
        assertThat(evaluatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEvaluatorWithPatch() throws Exception {
        // Initialize the database
        evaluatorRepository.saveAndFlush(evaluator);

        int databaseSizeBeforeUpdate = evaluatorRepository.findAll().size();

        // Update the evaluator using partial update
        Evaluator partialUpdatedEvaluator = new Evaluator();
        partialUpdatedEvaluator.setId(evaluator.getId());

        partialUpdatedEvaluator.address(UPDATED_ADDRESS);

        restEvaluatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluator))
            )
            .andExpect(status().isOk());

        // Validate the Evaluator in the database
        List<Evaluator> evaluatorList = evaluatorRepository.findAll();
        assertThat(evaluatorList).hasSize(databaseSizeBeforeUpdate);
        Evaluator testEvaluator = evaluatorList.get(evaluatorList.size() - 1);
        assertThat(testEvaluator.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testEvaluator.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEvaluator.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void fullUpdateEvaluatorWithPatch() throws Exception {
        // Initialize the database
        evaluatorRepository.saveAndFlush(evaluator);

        int databaseSizeBeforeUpdate = evaluatorRepository.findAll().size();

        // Update the evaluator using partial update
        Evaluator partialUpdatedEvaluator = new Evaluator();
        partialUpdatedEvaluator.setId(evaluator.getId());

        partialUpdatedEvaluator.phoneNumber(UPDATED_PHONE_NUMBER).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);

        restEvaluatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluator))
            )
            .andExpect(status().isOk());

        // Validate the Evaluator in the database
        List<Evaluator> evaluatorList = evaluatorRepository.findAll();
        assertThat(evaluatorList).hasSize(databaseSizeBeforeUpdate);
        Evaluator testEvaluator = evaluatorList.get(evaluatorList.size() - 1);
        assertThat(testEvaluator.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testEvaluator.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEvaluator.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void patchNonExistingEvaluator() throws Exception {
        int databaseSizeBeforeUpdate = evaluatorRepository.findAll().size();
        evaluator.setId(count.incrementAndGet());

        // Create the Evaluator
        EvaluatorDTO evaluatorDTO = evaluatorMapper.toDto(evaluator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, evaluatorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evaluator in the database
        List<Evaluator> evaluatorList = evaluatorRepository.findAll();
        assertThat(evaluatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvaluator() throws Exception {
        int databaseSizeBeforeUpdate = evaluatorRepository.findAll().size();
        evaluator.setId(count.incrementAndGet());

        // Create the Evaluator
        EvaluatorDTO evaluatorDTO = evaluatorMapper.toDto(evaluator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evaluator in the database
        List<Evaluator> evaluatorList = evaluatorRepository.findAll();
        assertThat(evaluatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvaluator() throws Exception {
        int databaseSizeBeforeUpdate = evaluatorRepository.findAll().size();
        evaluator.setId(count.incrementAndGet());

        // Create the Evaluator
        EvaluatorDTO evaluatorDTO = evaluatorMapper.toDto(evaluator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluatorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(evaluatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Evaluator in the database
        List<Evaluator> evaluatorList = evaluatorRepository.findAll();
        assertThat(evaluatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvaluator() throws Exception {
        // Initialize the database
        evaluatorRepository.saveAndFlush(evaluator);

        int databaseSizeBeforeDelete = evaluatorRepository.findAll().size();

        // Delete the evaluator
        restEvaluatorMockMvc
            .perform(delete(ENTITY_API_URL_ID, evaluator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Evaluator> evaluatorList = evaluatorRepository.findAll();
        assertThat(evaluatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
