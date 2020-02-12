package com.abrid.dropme.web.rest;

import com.abrid.dropme.DropMeApp;
import com.abrid.dropme.domain.TransporterAccount;
import com.abrid.dropme.domain.User;
import com.abrid.dropme.domain.Truck;
import com.abrid.dropme.domain.Reputation;
import com.abrid.dropme.repository.TransporterAccountRepository;
import com.abrid.dropme.service.TransporterAccountService;
import com.abrid.dropme.service.dto.TransporterAccountDTO;
import com.abrid.dropme.service.mapper.TransporterAccountMapper;
import com.abrid.dropme.web.rest.errors.ExceptionTranslator;
import com.abrid.dropme.service.dto.TransporterAccountCriteria;
import com.abrid.dropme.service.TransporterAccountQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.abrid.dropme.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TransporterAccountResource} REST controller.
 */
@SpringBootTest(classes = DropMeApp.class)
public class TransporterAccountResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PATENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PATENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PATENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PATENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_MANAGER_F_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_F_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_L_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_L_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_BALANCE = 1;
    private static final Integer UPDATED_BALANCE = 2;
    private static final Integer SMALLER_BALANCE = 1 - 1;

    private static final byte[] DEFAULT_INSURANCE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_INSURANCE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_INSURANCE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_INSURANCE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_REFERAL = "AAAAAAAAAA";
    private static final String UPDATED_REFERAL = "BBBBBBBBBB";

    private static final String DEFAULT_REFERED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REFERED_BY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    @Autowired
    private TransporterAccountRepository transporterAccountRepository;

    @Autowired
    private TransporterAccountMapper transporterAccountMapper;

    @Autowired
    private TransporterAccountService transporterAccountService;

    @Autowired
    private TransporterAccountQueryService transporterAccountQueryService;

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

    private MockMvc restTransporterAccountMockMvc;

    private TransporterAccount transporterAccount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransporterAccountResource transporterAccountResource = new TransporterAccountResource(transporterAccountService, transporterAccountQueryService);
        this.restTransporterAccountMockMvc = MockMvcBuilders.standaloneSetup(transporterAccountResource)
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
    public static TransporterAccount createEntity(EntityManager em) {
        TransporterAccount transporterAccount = new TransporterAccount()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .patent(DEFAULT_PATENT)
            .patentContentType(DEFAULT_PATENT_CONTENT_TYPE)
            .managerFName(DEFAULT_MANAGER_F_NAME)
            .managerLName(DEFAULT_MANAGER_L_NAME)
            .balance(DEFAULT_BALANCE)
            .insurance(DEFAULT_INSURANCE)
            .insuranceContentType(DEFAULT_INSURANCE_CONTENT_TYPE)
            .referal(DEFAULT_REFERAL)
            .referedBy(DEFAULT_REFERED_BY)
            .activated(DEFAULT_ACTIVATED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        transporterAccount.setUser(user);
        return transporterAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransporterAccount createUpdatedEntity(EntityManager em) {
        TransporterAccount transporterAccount = new TransporterAccount()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .patent(UPDATED_PATENT)
            .patentContentType(UPDATED_PATENT_CONTENT_TYPE)
            .managerFName(UPDATED_MANAGER_F_NAME)
            .managerLName(UPDATED_MANAGER_L_NAME)
            .balance(UPDATED_BALANCE)
            .insurance(UPDATED_INSURANCE)
            .insuranceContentType(UPDATED_INSURANCE_CONTENT_TYPE)
            .referal(UPDATED_REFERAL)
            .referedBy(UPDATED_REFERED_BY)
            .activated(UPDATED_ACTIVATED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        transporterAccount.setUser(user);
        return transporterAccount;
    }

    @BeforeEach
    public void initTest() {
        transporterAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransporterAccount() throws Exception {
        int databaseSizeBeforeCreate = transporterAccountRepository.findAll().size();

        // Create the TransporterAccount
        TransporterAccountDTO transporterAccountDTO = transporterAccountMapper.toDto(transporterAccount);
        restTransporterAccountMockMvc.perform(post("/api/transporter-accounts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transporterAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the TransporterAccount in the database
        List<TransporterAccount> transporterAccountList = transporterAccountRepository.findAll();
        assertThat(transporterAccountList).hasSize(databaseSizeBeforeCreate + 1);
        TransporterAccount testTransporterAccount = transporterAccountList.get(transporterAccountList.size() - 1);
        assertThat(testTransporterAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTransporterAccount.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testTransporterAccount.getPatent()).isEqualTo(DEFAULT_PATENT);
        assertThat(testTransporterAccount.getPatentContentType()).isEqualTo(DEFAULT_PATENT_CONTENT_TYPE);
        assertThat(testTransporterAccount.getManagerFName()).isEqualTo(DEFAULT_MANAGER_F_NAME);
        assertThat(testTransporterAccount.getManagerLName()).isEqualTo(DEFAULT_MANAGER_L_NAME);
        assertThat(testTransporterAccount.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testTransporterAccount.getInsurance()).isEqualTo(DEFAULT_INSURANCE);
        assertThat(testTransporterAccount.getInsuranceContentType()).isEqualTo(DEFAULT_INSURANCE_CONTENT_TYPE);
        assertThat(testTransporterAccount.getReferal()).isEqualTo(DEFAULT_REFERAL);
        assertThat(testTransporterAccount.getReferedBy()).isEqualTo(DEFAULT_REFERED_BY);
        assertThat(testTransporterAccount.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
    }

    @Test
    @Transactional
    public void createTransporterAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transporterAccountRepository.findAll().size();

        // Create the TransporterAccount with an existing ID
        transporterAccount.setId(1L);
        TransporterAccountDTO transporterAccountDTO = transporterAccountMapper.toDto(transporterAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransporterAccountMockMvc.perform(post("/api/transporter-accounts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transporterAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransporterAccount in the database
        List<TransporterAccount> transporterAccountList = transporterAccountRepository.findAll();
        assertThat(transporterAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTransporterAccounts() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList
        restTransporterAccountMockMvc.perform(get("/api/transporter-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transporterAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].patentContentType").value(hasItem(DEFAULT_PATENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].patent").value(hasItem(Base64Utils.encodeToString(DEFAULT_PATENT))))
            .andExpect(jsonPath("$.[*].managerFName").value(hasItem(DEFAULT_MANAGER_F_NAME)))
            .andExpect(jsonPath("$.[*].managerLName").value(hasItem(DEFAULT_MANAGER_L_NAME)))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.[*].insuranceContentType").value(hasItem(DEFAULT_INSURANCE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].insurance").value(hasItem(Base64Utils.encodeToString(DEFAULT_INSURANCE))))
            .andExpect(jsonPath("$.[*].referal").value(hasItem(DEFAULT_REFERAL)))
            .andExpect(jsonPath("$.[*].referedBy").value(hasItem(DEFAULT_REFERED_BY)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTransporterAccount() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get the transporterAccount
        restTransporterAccountMockMvc.perform(get("/api/transporter-accounts/{id}", transporterAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transporterAccount.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.patentContentType").value(DEFAULT_PATENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.patent").value(Base64Utils.encodeToString(DEFAULT_PATENT)))
            .andExpect(jsonPath("$.managerFName").value(DEFAULT_MANAGER_F_NAME))
            .andExpect(jsonPath("$.managerLName").value(DEFAULT_MANAGER_L_NAME))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE))
            .andExpect(jsonPath("$.insuranceContentType").value(DEFAULT_INSURANCE_CONTENT_TYPE))
            .andExpect(jsonPath("$.insurance").value(Base64Utils.encodeToString(DEFAULT_INSURANCE)))
            .andExpect(jsonPath("$.referal").value(DEFAULT_REFERAL))
            .andExpect(jsonPath("$.referedBy").value(DEFAULT_REFERED_BY))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()));
    }


    @Test
    @Transactional
    public void getTransporterAccountsByIdFiltering() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        Long id = transporterAccount.getId();

        defaultTransporterAccountShouldBeFound("id.equals=" + id);
        defaultTransporterAccountShouldNotBeFound("id.notEquals=" + id);

        defaultTransporterAccountShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransporterAccountShouldNotBeFound("id.greaterThan=" + id);

        defaultTransporterAccountShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransporterAccountShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTransporterAccountsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where name equals to DEFAULT_NAME
        defaultTransporterAccountShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the transporterAccountList where name equals to UPDATED_NAME
        defaultTransporterAccountShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where name not equals to DEFAULT_NAME
        defaultTransporterAccountShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the transporterAccountList where name not equals to UPDATED_NAME
        defaultTransporterAccountShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTransporterAccountShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the transporterAccountList where name equals to UPDATED_NAME
        defaultTransporterAccountShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where name is not null
        defaultTransporterAccountShouldBeFound("name.specified=true");

        // Get all the transporterAccountList where name is null
        defaultTransporterAccountShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransporterAccountsByNameContainsSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where name contains DEFAULT_NAME
        defaultTransporterAccountShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the transporterAccountList where name contains UPDATED_NAME
        defaultTransporterAccountShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where name does not contain DEFAULT_NAME
        defaultTransporterAccountShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the transporterAccountList where name does not contain UPDATED_NAME
        defaultTransporterAccountShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTransporterAccountsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where phone equals to DEFAULT_PHONE
        defaultTransporterAccountShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the transporterAccountList where phone equals to UPDATED_PHONE
        defaultTransporterAccountShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where phone not equals to DEFAULT_PHONE
        defaultTransporterAccountShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the transporterAccountList where phone not equals to UPDATED_PHONE
        defaultTransporterAccountShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultTransporterAccountShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the transporterAccountList where phone equals to UPDATED_PHONE
        defaultTransporterAccountShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where phone is not null
        defaultTransporterAccountShouldBeFound("phone.specified=true");

        // Get all the transporterAccountList where phone is null
        defaultTransporterAccountShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransporterAccountsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where phone contains DEFAULT_PHONE
        defaultTransporterAccountShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the transporterAccountList where phone contains UPDATED_PHONE
        defaultTransporterAccountShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where phone does not contain DEFAULT_PHONE
        defaultTransporterAccountShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the transporterAccountList where phone does not contain UPDATED_PHONE
        defaultTransporterAccountShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllTransporterAccountsByManagerFNameIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where managerFName equals to DEFAULT_MANAGER_F_NAME
        defaultTransporterAccountShouldBeFound("managerFName.equals=" + DEFAULT_MANAGER_F_NAME);

        // Get all the transporterAccountList where managerFName equals to UPDATED_MANAGER_F_NAME
        defaultTransporterAccountShouldNotBeFound("managerFName.equals=" + UPDATED_MANAGER_F_NAME);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByManagerFNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where managerFName not equals to DEFAULT_MANAGER_F_NAME
        defaultTransporterAccountShouldNotBeFound("managerFName.notEquals=" + DEFAULT_MANAGER_F_NAME);

        // Get all the transporterAccountList where managerFName not equals to UPDATED_MANAGER_F_NAME
        defaultTransporterAccountShouldBeFound("managerFName.notEquals=" + UPDATED_MANAGER_F_NAME);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByManagerFNameIsInShouldWork() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where managerFName in DEFAULT_MANAGER_F_NAME or UPDATED_MANAGER_F_NAME
        defaultTransporterAccountShouldBeFound("managerFName.in=" + DEFAULT_MANAGER_F_NAME + "," + UPDATED_MANAGER_F_NAME);

        // Get all the transporterAccountList where managerFName equals to UPDATED_MANAGER_F_NAME
        defaultTransporterAccountShouldNotBeFound("managerFName.in=" + UPDATED_MANAGER_F_NAME);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByManagerFNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where managerFName is not null
        defaultTransporterAccountShouldBeFound("managerFName.specified=true");

        // Get all the transporterAccountList where managerFName is null
        defaultTransporterAccountShouldNotBeFound("managerFName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransporterAccountsByManagerFNameContainsSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where managerFName contains DEFAULT_MANAGER_F_NAME
        defaultTransporterAccountShouldBeFound("managerFName.contains=" + DEFAULT_MANAGER_F_NAME);

        // Get all the transporterAccountList where managerFName contains UPDATED_MANAGER_F_NAME
        defaultTransporterAccountShouldNotBeFound("managerFName.contains=" + UPDATED_MANAGER_F_NAME);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByManagerFNameNotContainsSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where managerFName does not contain DEFAULT_MANAGER_F_NAME
        defaultTransporterAccountShouldNotBeFound("managerFName.doesNotContain=" + DEFAULT_MANAGER_F_NAME);

        // Get all the transporterAccountList where managerFName does not contain UPDATED_MANAGER_F_NAME
        defaultTransporterAccountShouldBeFound("managerFName.doesNotContain=" + UPDATED_MANAGER_F_NAME);
    }


    @Test
    @Transactional
    public void getAllTransporterAccountsByManagerLNameIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where managerLName equals to DEFAULT_MANAGER_L_NAME
        defaultTransporterAccountShouldBeFound("managerLName.equals=" + DEFAULT_MANAGER_L_NAME);

        // Get all the transporterAccountList where managerLName equals to UPDATED_MANAGER_L_NAME
        defaultTransporterAccountShouldNotBeFound("managerLName.equals=" + UPDATED_MANAGER_L_NAME);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByManagerLNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where managerLName not equals to DEFAULT_MANAGER_L_NAME
        defaultTransporterAccountShouldNotBeFound("managerLName.notEquals=" + DEFAULT_MANAGER_L_NAME);

        // Get all the transporterAccountList where managerLName not equals to UPDATED_MANAGER_L_NAME
        defaultTransporterAccountShouldBeFound("managerLName.notEquals=" + UPDATED_MANAGER_L_NAME);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByManagerLNameIsInShouldWork() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where managerLName in DEFAULT_MANAGER_L_NAME or UPDATED_MANAGER_L_NAME
        defaultTransporterAccountShouldBeFound("managerLName.in=" + DEFAULT_MANAGER_L_NAME + "," + UPDATED_MANAGER_L_NAME);

        // Get all the transporterAccountList where managerLName equals to UPDATED_MANAGER_L_NAME
        defaultTransporterAccountShouldNotBeFound("managerLName.in=" + UPDATED_MANAGER_L_NAME);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByManagerLNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where managerLName is not null
        defaultTransporterAccountShouldBeFound("managerLName.specified=true");

        // Get all the transporterAccountList where managerLName is null
        defaultTransporterAccountShouldNotBeFound("managerLName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransporterAccountsByManagerLNameContainsSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where managerLName contains DEFAULT_MANAGER_L_NAME
        defaultTransporterAccountShouldBeFound("managerLName.contains=" + DEFAULT_MANAGER_L_NAME);

        // Get all the transporterAccountList where managerLName contains UPDATED_MANAGER_L_NAME
        defaultTransporterAccountShouldNotBeFound("managerLName.contains=" + UPDATED_MANAGER_L_NAME);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByManagerLNameNotContainsSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where managerLName does not contain DEFAULT_MANAGER_L_NAME
        defaultTransporterAccountShouldNotBeFound("managerLName.doesNotContain=" + DEFAULT_MANAGER_L_NAME);

        // Get all the transporterAccountList where managerLName does not contain UPDATED_MANAGER_L_NAME
        defaultTransporterAccountShouldBeFound("managerLName.doesNotContain=" + UPDATED_MANAGER_L_NAME);
    }


    @Test
    @Transactional
    public void getAllTransporterAccountsByBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where balance equals to DEFAULT_BALANCE
        defaultTransporterAccountShouldBeFound("balance.equals=" + DEFAULT_BALANCE);

        // Get all the transporterAccountList where balance equals to UPDATED_BALANCE
        defaultTransporterAccountShouldNotBeFound("balance.equals=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByBalanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where balance not equals to DEFAULT_BALANCE
        defaultTransporterAccountShouldNotBeFound("balance.notEquals=" + DEFAULT_BALANCE);

        // Get all the transporterAccountList where balance not equals to UPDATED_BALANCE
        defaultTransporterAccountShouldBeFound("balance.notEquals=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where balance in DEFAULT_BALANCE or UPDATED_BALANCE
        defaultTransporterAccountShouldBeFound("balance.in=" + DEFAULT_BALANCE + "," + UPDATED_BALANCE);

        // Get all the transporterAccountList where balance equals to UPDATED_BALANCE
        defaultTransporterAccountShouldNotBeFound("balance.in=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where balance is not null
        defaultTransporterAccountShouldBeFound("balance.specified=true");

        // Get all the transporterAccountList where balance is null
        defaultTransporterAccountShouldNotBeFound("balance.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByBalanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where balance is greater than or equal to DEFAULT_BALANCE
        defaultTransporterAccountShouldBeFound("balance.greaterThanOrEqual=" + DEFAULT_BALANCE);

        // Get all the transporterAccountList where balance is greater than or equal to UPDATED_BALANCE
        defaultTransporterAccountShouldNotBeFound("balance.greaterThanOrEqual=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByBalanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where balance is less than or equal to DEFAULT_BALANCE
        defaultTransporterAccountShouldBeFound("balance.lessThanOrEqual=" + DEFAULT_BALANCE);

        // Get all the transporterAccountList where balance is less than or equal to SMALLER_BALANCE
        defaultTransporterAccountShouldNotBeFound("balance.lessThanOrEqual=" + SMALLER_BALANCE);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByBalanceIsLessThanSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where balance is less than DEFAULT_BALANCE
        defaultTransporterAccountShouldNotBeFound("balance.lessThan=" + DEFAULT_BALANCE);

        // Get all the transporterAccountList where balance is less than UPDATED_BALANCE
        defaultTransporterAccountShouldBeFound("balance.lessThan=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByBalanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where balance is greater than DEFAULT_BALANCE
        defaultTransporterAccountShouldNotBeFound("balance.greaterThan=" + DEFAULT_BALANCE);

        // Get all the transporterAccountList where balance is greater than SMALLER_BALANCE
        defaultTransporterAccountShouldBeFound("balance.greaterThan=" + SMALLER_BALANCE);
    }


    @Test
    @Transactional
    public void getAllTransporterAccountsByReferalIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where referal equals to DEFAULT_REFERAL
        defaultTransporterAccountShouldBeFound("referal.equals=" + DEFAULT_REFERAL);

        // Get all the transporterAccountList where referal equals to UPDATED_REFERAL
        defaultTransporterAccountShouldNotBeFound("referal.equals=" + UPDATED_REFERAL);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByReferalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where referal not equals to DEFAULT_REFERAL
        defaultTransporterAccountShouldNotBeFound("referal.notEquals=" + DEFAULT_REFERAL);

        // Get all the transporterAccountList where referal not equals to UPDATED_REFERAL
        defaultTransporterAccountShouldBeFound("referal.notEquals=" + UPDATED_REFERAL);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByReferalIsInShouldWork() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where referal in DEFAULT_REFERAL or UPDATED_REFERAL
        defaultTransporterAccountShouldBeFound("referal.in=" + DEFAULT_REFERAL + "," + UPDATED_REFERAL);

        // Get all the transporterAccountList where referal equals to UPDATED_REFERAL
        defaultTransporterAccountShouldNotBeFound("referal.in=" + UPDATED_REFERAL);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByReferalIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where referal is not null
        defaultTransporterAccountShouldBeFound("referal.specified=true");

        // Get all the transporterAccountList where referal is null
        defaultTransporterAccountShouldNotBeFound("referal.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransporterAccountsByReferalContainsSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where referal contains DEFAULT_REFERAL
        defaultTransporterAccountShouldBeFound("referal.contains=" + DEFAULT_REFERAL);

        // Get all the transporterAccountList where referal contains UPDATED_REFERAL
        defaultTransporterAccountShouldNotBeFound("referal.contains=" + UPDATED_REFERAL);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByReferalNotContainsSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where referal does not contain DEFAULT_REFERAL
        defaultTransporterAccountShouldNotBeFound("referal.doesNotContain=" + DEFAULT_REFERAL);

        // Get all the transporterAccountList where referal does not contain UPDATED_REFERAL
        defaultTransporterAccountShouldBeFound("referal.doesNotContain=" + UPDATED_REFERAL);
    }


    @Test
    @Transactional
    public void getAllTransporterAccountsByReferedByIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where referedBy equals to DEFAULT_REFERED_BY
        defaultTransporterAccountShouldBeFound("referedBy.equals=" + DEFAULT_REFERED_BY);

        // Get all the transporterAccountList where referedBy equals to UPDATED_REFERED_BY
        defaultTransporterAccountShouldNotBeFound("referedBy.equals=" + UPDATED_REFERED_BY);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByReferedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where referedBy not equals to DEFAULT_REFERED_BY
        defaultTransporterAccountShouldNotBeFound("referedBy.notEquals=" + DEFAULT_REFERED_BY);

        // Get all the transporterAccountList where referedBy not equals to UPDATED_REFERED_BY
        defaultTransporterAccountShouldBeFound("referedBy.notEquals=" + UPDATED_REFERED_BY);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByReferedByIsInShouldWork() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where referedBy in DEFAULT_REFERED_BY or UPDATED_REFERED_BY
        defaultTransporterAccountShouldBeFound("referedBy.in=" + DEFAULT_REFERED_BY + "," + UPDATED_REFERED_BY);

        // Get all the transporterAccountList where referedBy equals to UPDATED_REFERED_BY
        defaultTransporterAccountShouldNotBeFound("referedBy.in=" + UPDATED_REFERED_BY);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByReferedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where referedBy is not null
        defaultTransporterAccountShouldBeFound("referedBy.specified=true");

        // Get all the transporterAccountList where referedBy is null
        defaultTransporterAccountShouldNotBeFound("referedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransporterAccountsByReferedByContainsSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where referedBy contains DEFAULT_REFERED_BY
        defaultTransporterAccountShouldBeFound("referedBy.contains=" + DEFAULT_REFERED_BY);

        // Get all the transporterAccountList where referedBy contains UPDATED_REFERED_BY
        defaultTransporterAccountShouldNotBeFound("referedBy.contains=" + UPDATED_REFERED_BY);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByReferedByNotContainsSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where referedBy does not contain DEFAULT_REFERED_BY
        defaultTransporterAccountShouldNotBeFound("referedBy.doesNotContain=" + DEFAULT_REFERED_BY);

        // Get all the transporterAccountList where referedBy does not contain UPDATED_REFERED_BY
        defaultTransporterAccountShouldBeFound("referedBy.doesNotContain=" + UPDATED_REFERED_BY);
    }


    @Test
    @Transactional
    public void getAllTransporterAccountsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where activated equals to DEFAULT_ACTIVATED
        defaultTransporterAccountShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the transporterAccountList where activated equals to UPDATED_ACTIVATED
        defaultTransporterAccountShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where activated not equals to DEFAULT_ACTIVATED
        defaultTransporterAccountShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the transporterAccountList where activated not equals to UPDATED_ACTIVATED
        defaultTransporterAccountShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultTransporterAccountShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the transporterAccountList where activated equals to UPDATED_ACTIVATED
        defaultTransporterAccountShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        // Get all the transporterAccountList where activated is not null
        defaultTransporterAccountShouldBeFound("activated.specified=true");

        // Get all the transporterAccountList where activated is null
        defaultTransporterAccountShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransporterAccountsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = transporterAccount.getUser();
        transporterAccountRepository.saveAndFlush(transporterAccount);
        Long userId = user.getId();

        // Get all the transporterAccountList where user equals to userId
        defaultTransporterAccountShouldBeFound("userId.equals=" + userId);

        // Get all the transporterAccountList where user equals to userId + 1
        defaultTransporterAccountShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllTransporterAccountsByTruckIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);
        Truck truck = TruckResourceIT.createEntity(em);
        em.persist(truck);
        em.flush();
        transporterAccount.addTruck(truck);
        transporterAccountRepository.saveAndFlush(transporterAccount);
        Long truckId = truck.getId();

        // Get all the transporterAccountList where truck equals to truckId
        defaultTransporterAccountShouldBeFound("truckId.equals=" + truckId);

        // Get all the transporterAccountList where truck equals to truckId + 1
        defaultTransporterAccountShouldNotBeFound("truckId.equals=" + (truckId + 1));
    }


    @Test
    @Transactional
    public void getAllTransporterAccountsByReputationIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);
        Reputation reputation = ReputationResourceIT.createEntity(em);
        em.persist(reputation);
        em.flush();
        transporterAccount.addReputation(reputation);
        transporterAccountRepository.saveAndFlush(transporterAccount);
        Long reputationId = reputation.getId();

        // Get all the transporterAccountList where reputation equals to reputationId
        defaultTransporterAccountShouldBeFound("reputationId.equals=" + reputationId);

        // Get all the transporterAccountList where reputation equals to reputationId + 1
        defaultTransporterAccountShouldNotBeFound("reputationId.equals=" + (reputationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransporterAccountShouldBeFound(String filter) throws Exception {
        restTransporterAccountMockMvc.perform(get("/api/transporter-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transporterAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].patentContentType").value(hasItem(DEFAULT_PATENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].patent").value(hasItem(Base64Utils.encodeToString(DEFAULT_PATENT))))
            .andExpect(jsonPath("$.[*].managerFName").value(hasItem(DEFAULT_MANAGER_F_NAME)))
            .andExpect(jsonPath("$.[*].managerLName").value(hasItem(DEFAULT_MANAGER_L_NAME)))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.[*].insuranceContentType").value(hasItem(DEFAULT_INSURANCE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].insurance").value(hasItem(Base64Utils.encodeToString(DEFAULT_INSURANCE))))
            .andExpect(jsonPath("$.[*].referal").value(hasItem(DEFAULT_REFERAL)))
            .andExpect(jsonPath("$.[*].referedBy").value(hasItem(DEFAULT_REFERED_BY)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())));

        // Check, that the count call also returns 1
        restTransporterAccountMockMvc.perform(get("/api/transporter-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransporterAccountShouldNotBeFound(String filter) throws Exception {
        restTransporterAccountMockMvc.perform(get("/api/transporter-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransporterAccountMockMvc.perform(get("/api/transporter-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTransporterAccount() throws Exception {
        // Get the transporterAccount
        restTransporterAccountMockMvc.perform(get("/api/transporter-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransporterAccount() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        int databaseSizeBeforeUpdate = transporterAccountRepository.findAll().size();

        // Update the transporterAccount
        TransporterAccount updatedTransporterAccount = transporterAccountRepository.findById(transporterAccount.getId()).get();
        // Disconnect from session so that the updates on updatedTransporterAccount are not directly saved in db
        em.detach(updatedTransporterAccount);
        updatedTransporterAccount
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .patent(UPDATED_PATENT)
            .patentContentType(UPDATED_PATENT_CONTENT_TYPE)
            .managerFName(UPDATED_MANAGER_F_NAME)
            .managerLName(UPDATED_MANAGER_L_NAME)
            .balance(UPDATED_BALANCE)
            .insurance(UPDATED_INSURANCE)
            .insuranceContentType(UPDATED_INSURANCE_CONTENT_TYPE)
            .referal(UPDATED_REFERAL)
            .referedBy(UPDATED_REFERED_BY)
            .activated(UPDATED_ACTIVATED);
        TransporterAccountDTO transporterAccountDTO = transporterAccountMapper.toDto(updatedTransporterAccount);

        restTransporterAccountMockMvc.perform(put("/api/transporter-accounts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transporterAccountDTO)))
            .andExpect(status().isOk());

        // Validate the TransporterAccount in the database
        List<TransporterAccount> transporterAccountList = transporterAccountRepository.findAll();
        assertThat(transporterAccountList).hasSize(databaseSizeBeforeUpdate);
        TransporterAccount testTransporterAccount = transporterAccountList.get(transporterAccountList.size() - 1);
        assertThat(testTransporterAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTransporterAccount.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testTransporterAccount.getPatent()).isEqualTo(UPDATED_PATENT);
        assertThat(testTransporterAccount.getPatentContentType()).isEqualTo(UPDATED_PATENT_CONTENT_TYPE);
        assertThat(testTransporterAccount.getManagerFName()).isEqualTo(UPDATED_MANAGER_F_NAME);
        assertThat(testTransporterAccount.getManagerLName()).isEqualTo(UPDATED_MANAGER_L_NAME);
        assertThat(testTransporterAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testTransporterAccount.getInsurance()).isEqualTo(UPDATED_INSURANCE);
        assertThat(testTransporterAccount.getInsuranceContentType()).isEqualTo(UPDATED_INSURANCE_CONTENT_TYPE);
        assertThat(testTransporterAccount.getReferal()).isEqualTo(UPDATED_REFERAL);
        assertThat(testTransporterAccount.getReferedBy()).isEqualTo(UPDATED_REFERED_BY);
        assertThat(testTransporterAccount.isActivated()).isEqualTo(UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void updateNonExistingTransporterAccount() throws Exception {
        int databaseSizeBeforeUpdate = transporterAccountRepository.findAll().size();

        // Create the TransporterAccount
        TransporterAccountDTO transporterAccountDTO = transporterAccountMapper.toDto(transporterAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransporterAccountMockMvc.perform(put("/api/transporter-accounts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transporterAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransporterAccount in the database
        List<TransporterAccount> transporterAccountList = transporterAccountRepository.findAll();
        assertThat(transporterAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransporterAccount() throws Exception {
        // Initialize the database
        transporterAccountRepository.saveAndFlush(transporterAccount);

        int databaseSizeBeforeDelete = transporterAccountRepository.findAll().size();

        // Delete the transporterAccount
        restTransporterAccountMockMvc.perform(delete("/api/transporter-accounts/{id}", transporterAccount.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransporterAccount> transporterAccountList = transporterAccountRepository.findAll();
        assertThat(transporterAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
