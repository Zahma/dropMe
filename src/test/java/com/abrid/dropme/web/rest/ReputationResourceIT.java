package com.abrid.dropme.web.rest;

import com.abrid.dropme.DropMeApp;
import com.abrid.dropme.domain.Reputation;
import com.abrid.dropme.domain.TransporterAccount;
import com.abrid.dropme.domain.ClientAccount;
import com.abrid.dropme.repository.ReputationRepository;
import com.abrid.dropme.service.ReputationService;
import com.abrid.dropme.service.dto.ReputationDTO;
import com.abrid.dropme.service.mapper.ReputationMapper;
import com.abrid.dropme.web.rest.errors.ExceptionTranslator;
import com.abrid.dropme.service.dto.ReputationCriteria;
import com.abrid.dropme.service.ReputationQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.abrid.dropme.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReputationResource} REST controller.
 */
@SpringBootTest(classes = DropMeApp.class)
public class ReputationResourceIT {

    private static final Integer DEFAULT_RATE = 1;
    private static final Integer UPDATED_RATE = 2;
    private static final Integer SMALLER_RATE = 1 - 1;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private ReputationRepository reputationRepository;

    @Autowired
    private ReputationMapper reputationMapper;

    @Autowired
    private ReputationService reputationService;

    @Autowired
    private ReputationQueryService reputationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restReputationMockMvc;

    private Reputation reputation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReputationResource reputationResource = new ReputationResource(reputationService, reputationQueryService);
        this.restReputationMockMvc = MockMvcBuilders.standaloneSetup(reputationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reputation createEntity(EntityManager em) {
        Reputation reputation = new Reputation()
            .rate(DEFAULT_RATE)
            .comment(DEFAULT_COMMENT);
        return reputation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reputation createUpdatedEntity(EntityManager em) {
        Reputation reputation = new Reputation()
            .rate(UPDATED_RATE)
            .comment(UPDATED_COMMENT);
        return reputation;
    }

    @BeforeEach
    public void initTest() {
        reputation = createEntity(em);
    }

    @Test
    @Transactional
    public void createReputation() throws Exception {
        int databaseSizeBeforeCreate = reputationRepository.findAll().size();

        // Create the Reputation
        ReputationDTO reputationDTO = reputationMapper.toDto(reputation);
        restReputationMockMvc.perform(post("/api/reputations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reputationDTO)))
            .andExpect(status().isCreated());

        // Validate the Reputation in the database
        List<Reputation> reputationList = reputationRepository.findAll();
        assertThat(reputationList).hasSize(databaseSizeBeforeCreate + 1);
        Reputation testReputation = reputationList.get(reputationList.size() - 1);
        assertThat(testReputation.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testReputation.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createReputationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reputationRepository.findAll().size();

        // Create the Reputation with an existing ID
        reputation.setId(1L);
        ReputationDTO reputationDTO = reputationMapper.toDto(reputation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReputationMockMvc.perform(post("/api/reputations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reputationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reputation in the database
        List<Reputation> reputationList = reputationRepository.findAll();
        assertThat(reputationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = reputationRepository.findAll().size();
        // set the field null
        reputation.setRate(null);

        // Create the Reputation, which fails.
        ReputationDTO reputationDTO = reputationMapper.toDto(reputation);

        restReputationMockMvc.perform(post("/api/reputations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reputationDTO)))
            .andExpect(status().isBadRequest());

        List<Reputation> reputationList = reputationRepository.findAll();
        assertThat(reputationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReputations() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get all the reputationList
        restReputationMockMvc.perform(get("/api/reputations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reputation.getId().intValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
    }
    
    @Test
    @Transactional
    public void getReputation() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get the reputation
        restReputationMockMvc.perform(get("/api/reputations/{id}", reputation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reputation.getId().intValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
    }


    @Test
    @Transactional
    public void getReputationsByIdFiltering() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        Long id = reputation.getId();

        defaultReputationShouldBeFound("id.equals=" + id);
        defaultReputationShouldNotBeFound("id.notEquals=" + id);

        defaultReputationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReputationShouldNotBeFound("id.greaterThan=" + id);

        defaultReputationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReputationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllReputationsByRateIsEqualToSomething() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get all the reputationList where rate equals to DEFAULT_RATE
        defaultReputationShouldBeFound("rate.equals=" + DEFAULT_RATE);

        // Get all the reputationList where rate equals to UPDATED_RATE
        defaultReputationShouldNotBeFound("rate.equals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllReputationsByRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get all the reputationList where rate not equals to DEFAULT_RATE
        defaultReputationShouldNotBeFound("rate.notEquals=" + DEFAULT_RATE);

        // Get all the reputationList where rate not equals to UPDATED_RATE
        defaultReputationShouldBeFound("rate.notEquals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllReputationsByRateIsInShouldWork() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get all the reputationList where rate in DEFAULT_RATE or UPDATED_RATE
        defaultReputationShouldBeFound("rate.in=" + DEFAULT_RATE + "," + UPDATED_RATE);

        // Get all the reputationList where rate equals to UPDATED_RATE
        defaultReputationShouldNotBeFound("rate.in=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllReputationsByRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get all the reputationList where rate is not null
        defaultReputationShouldBeFound("rate.specified=true");

        // Get all the reputationList where rate is null
        defaultReputationShouldNotBeFound("rate.specified=false");
    }

    @Test
    @Transactional
    public void getAllReputationsByRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get all the reputationList where rate is greater than or equal to DEFAULT_RATE
        defaultReputationShouldBeFound("rate.greaterThanOrEqual=" + DEFAULT_RATE);

        // Get all the reputationList where rate is greater than or equal to UPDATED_RATE
        defaultReputationShouldNotBeFound("rate.greaterThanOrEqual=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllReputationsByRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get all the reputationList where rate is less than or equal to DEFAULT_RATE
        defaultReputationShouldBeFound("rate.lessThanOrEqual=" + DEFAULT_RATE);

        // Get all the reputationList where rate is less than or equal to SMALLER_RATE
        defaultReputationShouldNotBeFound("rate.lessThanOrEqual=" + SMALLER_RATE);
    }

    @Test
    @Transactional
    public void getAllReputationsByRateIsLessThanSomething() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get all the reputationList where rate is less than DEFAULT_RATE
        defaultReputationShouldNotBeFound("rate.lessThan=" + DEFAULT_RATE);

        // Get all the reputationList where rate is less than UPDATED_RATE
        defaultReputationShouldBeFound("rate.lessThan=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllReputationsByRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get all the reputationList where rate is greater than DEFAULT_RATE
        defaultReputationShouldNotBeFound("rate.greaterThan=" + DEFAULT_RATE);

        // Get all the reputationList where rate is greater than SMALLER_RATE
        defaultReputationShouldBeFound("rate.greaterThan=" + SMALLER_RATE);
    }


    @Test
    @Transactional
    public void getAllReputationsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get all the reputationList where comment equals to DEFAULT_COMMENT
        defaultReputationShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the reputationList where comment equals to UPDATED_COMMENT
        defaultReputationShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllReputationsByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get all the reputationList where comment not equals to DEFAULT_COMMENT
        defaultReputationShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the reputationList where comment not equals to UPDATED_COMMENT
        defaultReputationShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllReputationsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get all the reputationList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultReputationShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the reputationList where comment equals to UPDATED_COMMENT
        defaultReputationShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllReputationsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get all the reputationList where comment is not null
        defaultReputationShouldBeFound("comment.specified=true");

        // Get all the reputationList where comment is null
        defaultReputationShouldNotBeFound("comment.specified=false");
    }
                @Test
    @Transactional
    public void getAllReputationsByCommentContainsSomething() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get all the reputationList where comment contains DEFAULT_COMMENT
        defaultReputationShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the reputationList where comment contains UPDATED_COMMENT
        defaultReputationShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllReputationsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        // Get all the reputationList where comment does not contain DEFAULT_COMMENT
        defaultReputationShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the reputationList where comment does not contain UPDATED_COMMENT
        defaultReputationShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }


    @Test
    @Transactional
    public void getAllReputationsByTransporterAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);
        TransporterAccount transporterAccount = TransporterAccountResourceIT.createEntity(em);
        em.persist(transporterAccount);
        em.flush();
        reputation.setTransporterAccount(transporterAccount);
        reputationRepository.saveAndFlush(reputation);
        Long transporterAccountId = transporterAccount.getId();

        // Get all the reputationList where transporterAccount equals to transporterAccountId
        defaultReputationShouldBeFound("transporterAccountId.equals=" + transporterAccountId);

        // Get all the reputationList where transporterAccount equals to transporterAccountId + 1
        defaultReputationShouldNotBeFound("transporterAccountId.equals=" + (transporterAccountId + 1));
    }


    @Test
    @Transactional
    public void getAllReputationsByClientAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);
        ClientAccount clientAccount = ClientAccountResourceIT.createEntity(em);
        em.persist(clientAccount);
        em.flush();
        reputation.setClientAccount(clientAccount);
        reputationRepository.saveAndFlush(reputation);
        Long clientAccountId = clientAccount.getId();

        // Get all the reputationList where clientAccount equals to clientAccountId
        defaultReputationShouldBeFound("clientAccountId.equals=" + clientAccountId);

        // Get all the reputationList where clientAccount equals to clientAccountId + 1
        defaultReputationShouldNotBeFound("clientAccountId.equals=" + (clientAccountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReputationShouldBeFound(String filter) throws Exception {
        restReputationMockMvc.perform(get("/api/reputations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reputation.getId().intValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));

        // Check, that the count call also returns 1
        restReputationMockMvc.perform(get("/api/reputations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReputationShouldNotBeFound(String filter) throws Exception {
        restReputationMockMvc.perform(get("/api/reputations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReputationMockMvc.perform(get("/api/reputations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReputation() throws Exception {
        // Get the reputation
        restReputationMockMvc.perform(get("/api/reputations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReputation() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        int databaseSizeBeforeUpdate = reputationRepository.findAll().size();

        // Update the reputation
        Reputation updatedReputation = reputationRepository.findById(reputation.getId()).get();
        // Disconnect from session so that the updates on updatedReputation are not directly saved in db
        em.detach(updatedReputation);
        updatedReputation
            .rate(UPDATED_RATE)
            .comment(UPDATED_COMMENT);
        ReputationDTO reputationDTO = reputationMapper.toDto(updatedReputation);

        restReputationMockMvc.perform(put("/api/reputations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reputationDTO)))
            .andExpect(status().isOk());

        // Validate the Reputation in the database
        List<Reputation> reputationList = reputationRepository.findAll();
        assertThat(reputationList).hasSize(databaseSizeBeforeUpdate);
        Reputation testReputation = reputationList.get(reputationList.size() - 1);
        assertThat(testReputation.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testReputation.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingReputation() throws Exception {
        int databaseSizeBeforeUpdate = reputationRepository.findAll().size();

        // Create the Reputation
        ReputationDTO reputationDTO = reputationMapper.toDto(reputation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReputationMockMvc.perform(put("/api/reputations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reputationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reputation in the database
        List<Reputation> reputationList = reputationRepository.findAll();
        assertThat(reputationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReputation() throws Exception {
        // Initialize the database
        reputationRepository.saveAndFlush(reputation);

        int databaseSizeBeforeDelete = reputationRepository.findAll().size();

        // Delete the reputation
        restReputationMockMvc.perform(delete("/api/reputations/{id}", reputation.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reputation> reputationList = reputationRepository.findAll();
        assertThat(reputationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
