package com.abrid.dropme.web.rest;

import com.abrid.dropme.DropMeApp;
import com.abrid.dropme.domain.ClientAccount;
import com.abrid.dropme.domain.User;
import com.abrid.dropme.domain.Reputation;
import com.abrid.dropme.domain.Trip;
import com.abrid.dropme.repository.ClientAccountRepository;
import com.abrid.dropme.service.ClientAccountService;
import com.abrid.dropme.service.dto.ClientAccountDTO;
import com.abrid.dropme.service.mapper.ClientAccountMapper;
import com.abrid.dropme.web.rest.errors.ExceptionTranslator;
import com.abrid.dropme.service.dto.ClientAccountCriteria;
import com.abrid.dropme.service.ClientAccountQueryService;

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
 * Integration tests for the {@link ClientAccountResource} REST controller.
 */
@SpringBootTest(classes = DropMeApp.class)
public class ClientAccountResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERRED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REFERRED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_REFERAL = "AAAAAAAAAA";
    private static final String UPDATED_REFERAL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    @Autowired
    private ClientAccountRepository clientAccountRepository;

    @Autowired
    private ClientAccountMapper clientAccountMapper;

    @Autowired
    private ClientAccountService clientAccountService;

    @Autowired
    private ClientAccountQueryService clientAccountQueryService;

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

    private MockMvc restClientAccountMockMvc;

    private ClientAccount clientAccount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientAccountResource clientAccountResource = new ClientAccountResource(clientAccountService, clientAccountQueryService);
        this.restClientAccountMockMvc = MockMvcBuilders.standaloneSetup(clientAccountResource)
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
    public static ClientAccount createEntity(EntityManager em) {
        ClientAccount clientAccount = new ClientAccount()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phone(DEFAULT_PHONE)
            .referredBy(DEFAULT_REFERRED_BY)
            .referal(DEFAULT_REFERAL)
            .activated(DEFAULT_ACTIVATED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        clientAccount.setUser(user);
        return clientAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientAccount createUpdatedEntity(EntityManager em) {
        ClientAccount clientAccount = new ClientAccount()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .referredBy(UPDATED_REFERRED_BY)
            .referal(UPDATED_REFERAL)
            .activated(UPDATED_ACTIVATED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        clientAccount.setUser(user);
        return clientAccount;
    }

    @BeforeEach
    public void initTest() {
        clientAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientAccount() throws Exception {
        int databaseSizeBeforeCreate = clientAccountRepository.findAll().size();

        // Create the ClientAccount
        ClientAccountDTO clientAccountDTO = clientAccountMapper.toDto(clientAccount);
        restClientAccountMockMvc.perform(post("/api/client-accounts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the ClientAccount in the database
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeCreate + 1);
        ClientAccount testClientAccount = clientAccountList.get(clientAccountList.size() - 1);
        assertThat(testClientAccount.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testClientAccount.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testClientAccount.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testClientAccount.getReferredBy()).isEqualTo(DEFAULT_REFERRED_BY);
        assertThat(testClientAccount.getReferal()).isEqualTo(DEFAULT_REFERAL);
        assertThat(testClientAccount.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
    }

    @Test
    @Transactional
    public void createClientAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientAccountRepository.findAll().size();

        // Create the ClientAccount with an existing ID
        clientAccount.setId(1L);
        ClientAccountDTO clientAccountDTO = clientAccountMapper.toDto(clientAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientAccountMockMvc.perform(post("/api/client-accounts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientAccount in the database
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClientAccounts() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList
        restClientAccountMockMvc.perform(get("/api/client-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].referredBy").value(hasItem(DEFAULT_REFERRED_BY)))
            .andExpect(jsonPath("$.[*].referal").value(hasItem(DEFAULT_REFERAL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getClientAccount() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get the clientAccount
        restClientAccountMockMvc.perform(get("/api/client-accounts/{id}", clientAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientAccount.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.referredBy").value(DEFAULT_REFERRED_BY))
            .andExpect(jsonPath("$.referal").value(DEFAULT_REFERAL))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()));
    }


    @Test
    @Transactional
    public void getClientAccountsByIdFiltering() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        Long id = clientAccount.getId();

        defaultClientAccountShouldBeFound("id.equals=" + id);
        defaultClientAccountShouldNotBeFound("id.notEquals=" + id);

        defaultClientAccountShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClientAccountShouldNotBeFound("id.greaterThan=" + id);

        defaultClientAccountShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClientAccountShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllClientAccountsByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where firstName equals to DEFAULT_FIRST_NAME
        defaultClientAccountShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the clientAccountList where firstName equals to UPDATED_FIRST_NAME
        defaultClientAccountShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where firstName not equals to DEFAULT_FIRST_NAME
        defaultClientAccountShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the clientAccountList where firstName not equals to UPDATED_FIRST_NAME
        defaultClientAccountShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultClientAccountShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the clientAccountList where firstName equals to UPDATED_FIRST_NAME
        defaultClientAccountShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where firstName is not null
        defaultClientAccountShouldBeFound("firstName.specified=true");

        // Get all the clientAccountList where firstName is null
        defaultClientAccountShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientAccountsByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where firstName contains DEFAULT_FIRST_NAME
        defaultClientAccountShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the clientAccountList where firstName contains UPDATED_FIRST_NAME
        defaultClientAccountShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where firstName does not contain DEFAULT_FIRST_NAME
        defaultClientAccountShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the clientAccountList where firstName does not contain UPDATED_FIRST_NAME
        defaultClientAccountShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllClientAccountsByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where lastName equals to DEFAULT_LAST_NAME
        defaultClientAccountShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the clientAccountList where lastName equals to UPDATED_LAST_NAME
        defaultClientAccountShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where lastName not equals to DEFAULT_LAST_NAME
        defaultClientAccountShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the clientAccountList where lastName not equals to UPDATED_LAST_NAME
        defaultClientAccountShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultClientAccountShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the clientAccountList where lastName equals to UPDATED_LAST_NAME
        defaultClientAccountShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where lastName is not null
        defaultClientAccountShouldBeFound("lastName.specified=true");

        // Get all the clientAccountList where lastName is null
        defaultClientAccountShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientAccountsByLastNameContainsSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where lastName contains DEFAULT_LAST_NAME
        defaultClientAccountShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the clientAccountList where lastName contains UPDATED_LAST_NAME
        defaultClientAccountShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where lastName does not contain DEFAULT_LAST_NAME
        defaultClientAccountShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the clientAccountList where lastName does not contain UPDATED_LAST_NAME
        defaultClientAccountShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllClientAccountsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where phone equals to DEFAULT_PHONE
        defaultClientAccountShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the clientAccountList where phone equals to UPDATED_PHONE
        defaultClientAccountShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where phone not equals to DEFAULT_PHONE
        defaultClientAccountShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the clientAccountList where phone not equals to UPDATED_PHONE
        defaultClientAccountShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultClientAccountShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the clientAccountList where phone equals to UPDATED_PHONE
        defaultClientAccountShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where phone is not null
        defaultClientAccountShouldBeFound("phone.specified=true");

        // Get all the clientAccountList where phone is null
        defaultClientAccountShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientAccountsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where phone contains DEFAULT_PHONE
        defaultClientAccountShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the clientAccountList where phone contains UPDATED_PHONE
        defaultClientAccountShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where phone does not contain DEFAULT_PHONE
        defaultClientAccountShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the clientAccountList where phone does not contain UPDATED_PHONE
        defaultClientAccountShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllClientAccountsByReferredByIsEqualToSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where referredBy equals to DEFAULT_REFERRED_BY
        defaultClientAccountShouldBeFound("referredBy.equals=" + DEFAULT_REFERRED_BY);

        // Get all the clientAccountList where referredBy equals to UPDATED_REFERRED_BY
        defaultClientAccountShouldNotBeFound("referredBy.equals=" + UPDATED_REFERRED_BY);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByReferredByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where referredBy not equals to DEFAULT_REFERRED_BY
        defaultClientAccountShouldNotBeFound("referredBy.notEquals=" + DEFAULT_REFERRED_BY);

        // Get all the clientAccountList where referredBy not equals to UPDATED_REFERRED_BY
        defaultClientAccountShouldBeFound("referredBy.notEquals=" + UPDATED_REFERRED_BY);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByReferredByIsInShouldWork() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where referredBy in DEFAULT_REFERRED_BY or UPDATED_REFERRED_BY
        defaultClientAccountShouldBeFound("referredBy.in=" + DEFAULT_REFERRED_BY + "," + UPDATED_REFERRED_BY);

        // Get all the clientAccountList where referredBy equals to UPDATED_REFERRED_BY
        defaultClientAccountShouldNotBeFound("referredBy.in=" + UPDATED_REFERRED_BY);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByReferredByIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where referredBy is not null
        defaultClientAccountShouldBeFound("referredBy.specified=true");

        // Get all the clientAccountList where referredBy is null
        defaultClientAccountShouldNotBeFound("referredBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientAccountsByReferredByContainsSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where referredBy contains DEFAULT_REFERRED_BY
        defaultClientAccountShouldBeFound("referredBy.contains=" + DEFAULT_REFERRED_BY);

        // Get all the clientAccountList where referredBy contains UPDATED_REFERRED_BY
        defaultClientAccountShouldNotBeFound("referredBy.contains=" + UPDATED_REFERRED_BY);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByReferredByNotContainsSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where referredBy does not contain DEFAULT_REFERRED_BY
        defaultClientAccountShouldNotBeFound("referredBy.doesNotContain=" + DEFAULT_REFERRED_BY);

        // Get all the clientAccountList where referredBy does not contain UPDATED_REFERRED_BY
        defaultClientAccountShouldBeFound("referredBy.doesNotContain=" + UPDATED_REFERRED_BY);
    }


    @Test
    @Transactional
    public void getAllClientAccountsByReferalIsEqualToSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where referal equals to DEFAULT_REFERAL
        defaultClientAccountShouldBeFound("referal.equals=" + DEFAULT_REFERAL);

        // Get all the clientAccountList where referal equals to UPDATED_REFERAL
        defaultClientAccountShouldNotBeFound("referal.equals=" + UPDATED_REFERAL);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByReferalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where referal not equals to DEFAULT_REFERAL
        defaultClientAccountShouldNotBeFound("referal.notEquals=" + DEFAULT_REFERAL);

        // Get all the clientAccountList where referal not equals to UPDATED_REFERAL
        defaultClientAccountShouldBeFound("referal.notEquals=" + UPDATED_REFERAL);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByReferalIsInShouldWork() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where referal in DEFAULT_REFERAL or UPDATED_REFERAL
        defaultClientAccountShouldBeFound("referal.in=" + DEFAULT_REFERAL + "," + UPDATED_REFERAL);

        // Get all the clientAccountList where referal equals to UPDATED_REFERAL
        defaultClientAccountShouldNotBeFound("referal.in=" + UPDATED_REFERAL);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByReferalIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where referal is not null
        defaultClientAccountShouldBeFound("referal.specified=true");

        // Get all the clientAccountList where referal is null
        defaultClientAccountShouldNotBeFound("referal.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientAccountsByReferalContainsSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where referal contains DEFAULT_REFERAL
        defaultClientAccountShouldBeFound("referal.contains=" + DEFAULT_REFERAL);

        // Get all the clientAccountList where referal contains UPDATED_REFERAL
        defaultClientAccountShouldNotBeFound("referal.contains=" + UPDATED_REFERAL);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByReferalNotContainsSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where referal does not contain DEFAULT_REFERAL
        defaultClientAccountShouldNotBeFound("referal.doesNotContain=" + DEFAULT_REFERAL);

        // Get all the clientAccountList where referal does not contain UPDATED_REFERAL
        defaultClientAccountShouldBeFound("referal.doesNotContain=" + UPDATED_REFERAL);
    }


    @Test
    @Transactional
    public void getAllClientAccountsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where activated equals to DEFAULT_ACTIVATED
        defaultClientAccountShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the clientAccountList where activated equals to UPDATED_ACTIVATED
        defaultClientAccountShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where activated not equals to DEFAULT_ACTIVATED
        defaultClientAccountShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the clientAccountList where activated not equals to UPDATED_ACTIVATED
        defaultClientAccountShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultClientAccountShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the clientAccountList where activated equals to UPDATED_ACTIVATED
        defaultClientAccountShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllClientAccountsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList where activated is not null
        defaultClientAccountShouldBeFound("activated.specified=true");

        // Get all the clientAccountList where activated is null
        defaultClientAccountShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientAccountsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = clientAccount.getUser();
        clientAccountRepository.saveAndFlush(clientAccount);
        Long userId = user.getId();

        // Get all the clientAccountList where user equals to userId
        defaultClientAccountShouldBeFound("userId.equals=" + userId);

        // Get all the clientAccountList where user equals to userId + 1
        defaultClientAccountShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllClientAccountsByReputationIsEqualToSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);
        Reputation reputation = ReputationResourceIT.createEntity(em);
        em.persist(reputation);
        em.flush();
        clientAccount.addReputation(reputation);
        clientAccountRepository.saveAndFlush(clientAccount);
        Long reputationId = reputation.getId();

        // Get all the clientAccountList where reputation equals to reputationId
        defaultClientAccountShouldBeFound("reputationId.equals=" + reputationId);

        // Get all the clientAccountList where reputation equals to reputationId + 1
        defaultClientAccountShouldNotBeFound("reputationId.equals=" + (reputationId + 1));
    }


    @Test
    @Transactional
    public void getAllClientAccountsByTripIsEqualToSomething() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);
        Trip trip = TripResourceIT.createEntity(em);
        em.persist(trip);
        em.flush();
        clientAccount.addTrip(trip);
        clientAccountRepository.saveAndFlush(clientAccount);
        Long tripId = trip.getId();

        // Get all the clientAccountList where trip equals to tripId
        defaultClientAccountShouldBeFound("tripId.equals=" + tripId);

        // Get all the clientAccountList where trip equals to tripId + 1
        defaultClientAccountShouldNotBeFound("tripId.equals=" + (tripId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClientAccountShouldBeFound(String filter) throws Exception {
        restClientAccountMockMvc.perform(get("/api/client-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].referredBy").value(hasItem(DEFAULT_REFERRED_BY)))
            .andExpect(jsonPath("$.[*].referal").value(hasItem(DEFAULT_REFERAL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())));

        // Check, that the count call also returns 1
        restClientAccountMockMvc.perform(get("/api/client-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClientAccountShouldNotBeFound(String filter) throws Exception {
        restClientAccountMockMvc.perform(get("/api/client-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClientAccountMockMvc.perform(get("/api/client-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingClientAccount() throws Exception {
        // Get the clientAccount
        restClientAccountMockMvc.perform(get("/api/client-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientAccount() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        int databaseSizeBeforeUpdate = clientAccountRepository.findAll().size();

        // Update the clientAccount
        ClientAccount updatedClientAccount = clientAccountRepository.findById(clientAccount.getId()).get();
        // Disconnect from session so that the updates on updatedClientAccount are not directly saved in db
        em.detach(updatedClientAccount);
        updatedClientAccount
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .referredBy(UPDATED_REFERRED_BY)
            .referal(UPDATED_REFERAL)
            .activated(UPDATED_ACTIVATED);
        ClientAccountDTO clientAccountDTO = clientAccountMapper.toDto(updatedClientAccount);

        restClientAccountMockMvc.perform(put("/api/client-accounts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientAccountDTO)))
            .andExpect(status().isOk());

        // Validate the ClientAccount in the database
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeUpdate);
        ClientAccount testClientAccount = clientAccountList.get(clientAccountList.size() - 1);
        assertThat(testClientAccount.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testClientAccount.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testClientAccount.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testClientAccount.getReferredBy()).isEqualTo(UPDATED_REFERRED_BY);
        assertThat(testClientAccount.getReferal()).isEqualTo(UPDATED_REFERAL);
        assertThat(testClientAccount.isActivated()).isEqualTo(UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void updateNonExistingClientAccount() throws Exception {
        int databaseSizeBeforeUpdate = clientAccountRepository.findAll().size();

        // Create the ClientAccount
        ClientAccountDTO clientAccountDTO = clientAccountMapper.toDto(clientAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientAccountMockMvc.perform(put("/api/client-accounts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientAccount in the database
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientAccount() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        int databaseSizeBeforeDelete = clientAccountRepository.findAll().size();

        // Delete the clientAccount
        restClientAccountMockMvc.perform(delete("/api/client-accounts/{id}", clientAccount.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
