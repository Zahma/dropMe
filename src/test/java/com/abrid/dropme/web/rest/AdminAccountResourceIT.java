package com.abrid.dropme.web.rest;

import com.abrid.dropme.DropMeApp;
import com.abrid.dropme.domain.AdminAccount;
import com.abrid.dropme.domain.User;
import com.abrid.dropme.repository.AdminAccountRepository;
import com.abrid.dropme.service.AdminAccountService;
import com.abrid.dropme.service.dto.AdminAccountDTO;
import com.abrid.dropme.service.mapper.AdminAccountMapper;
import com.abrid.dropme.web.rest.errors.ExceptionTranslator;
import com.abrid.dropme.service.dto.AdminAccountCriteria;
import com.abrid.dropme.service.AdminAccountQueryService;

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

import com.abrid.dropme.domain.enumeration.ERole;
/**
 * Integration tests for the {@link AdminAccountResource} REST controller.
 */
@SpringBootTest(classes = DropMeApp.class)
public class AdminAccountResourceIT {

    private static final ERole DEFAULT_ROLE = ERole.SUPERADMIN;
    private static final ERole UPDATED_ROLE = ERole.ADMIN;

    @Autowired
    private AdminAccountRepository adminAccountRepository;

    @Autowired
    private AdminAccountMapper adminAccountMapper;

    @Autowired
    private AdminAccountService adminAccountService;

    @Autowired
    private AdminAccountQueryService adminAccountQueryService;

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

    private MockMvc restAdminAccountMockMvc;

    private AdminAccount adminAccount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdminAccountResource adminAccountResource = new AdminAccountResource(adminAccountService, adminAccountQueryService);
        this.restAdminAccountMockMvc = MockMvcBuilders.standaloneSetup(adminAccountResource)
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
    public static AdminAccount createEntity(EntityManager em) {
        AdminAccount adminAccount = new AdminAccount()
            .role(DEFAULT_ROLE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        adminAccount.setUser(user);
        return adminAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminAccount createUpdatedEntity(EntityManager em) {
        AdminAccount adminAccount = new AdminAccount()
            .role(UPDATED_ROLE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        adminAccount.setUser(user);
        return adminAccount;
    }

    @BeforeEach
    public void initTest() {
        adminAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdminAccount() throws Exception {
        int databaseSizeBeforeCreate = adminAccountRepository.findAll().size();

        // Create the AdminAccount
        AdminAccountDTO adminAccountDTO = adminAccountMapper.toDto(adminAccount);
        restAdminAccountMockMvc.perform(post("/api/admin-accounts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adminAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the AdminAccount in the database
        List<AdminAccount> adminAccountList = adminAccountRepository.findAll();
        assertThat(adminAccountList).hasSize(databaseSizeBeforeCreate + 1);
        AdminAccount testAdminAccount = adminAccountList.get(adminAccountList.size() - 1);
        assertThat(testAdminAccount.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    public void createAdminAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adminAccountRepository.findAll().size();

        // Create the AdminAccount with an existing ID
        adminAccount.setId(1L);
        AdminAccountDTO adminAccountDTO = adminAccountMapper.toDto(adminAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminAccountMockMvc.perform(post("/api/admin-accounts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adminAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdminAccount in the database
        List<AdminAccount> adminAccountList = adminAccountRepository.findAll();
        assertThat(adminAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAdminAccounts() throws Exception {
        // Initialize the database
        adminAccountRepository.saveAndFlush(adminAccount);

        // Get all the adminAccountList
        restAdminAccountMockMvc.perform(get("/api/admin-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }
    
    @Test
    @Transactional
    public void getAdminAccount() throws Exception {
        // Initialize the database
        adminAccountRepository.saveAndFlush(adminAccount);

        // Get the adminAccount
        restAdminAccountMockMvc.perform(get("/api/admin-accounts/{id}", adminAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adminAccount.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }


    @Test
    @Transactional
    public void getAdminAccountsByIdFiltering() throws Exception {
        // Initialize the database
        adminAccountRepository.saveAndFlush(adminAccount);

        Long id = adminAccount.getId();

        defaultAdminAccountShouldBeFound("id.equals=" + id);
        defaultAdminAccountShouldNotBeFound("id.notEquals=" + id);

        defaultAdminAccountShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdminAccountShouldNotBeFound("id.greaterThan=" + id);

        defaultAdminAccountShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdminAccountShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdminAccountsByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        adminAccountRepository.saveAndFlush(adminAccount);

        // Get all the adminAccountList where role equals to DEFAULT_ROLE
        defaultAdminAccountShouldBeFound("role.equals=" + DEFAULT_ROLE);

        // Get all the adminAccountList where role equals to UPDATED_ROLE
        defaultAdminAccountShouldNotBeFound("role.equals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void getAllAdminAccountsByRoleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adminAccountRepository.saveAndFlush(adminAccount);

        // Get all the adminAccountList where role not equals to DEFAULT_ROLE
        defaultAdminAccountShouldNotBeFound("role.notEquals=" + DEFAULT_ROLE);

        // Get all the adminAccountList where role not equals to UPDATED_ROLE
        defaultAdminAccountShouldBeFound("role.notEquals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void getAllAdminAccountsByRoleIsInShouldWork() throws Exception {
        // Initialize the database
        adminAccountRepository.saveAndFlush(adminAccount);

        // Get all the adminAccountList where role in DEFAULT_ROLE or UPDATED_ROLE
        defaultAdminAccountShouldBeFound("role.in=" + DEFAULT_ROLE + "," + UPDATED_ROLE);

        // Get all the adminAccountList where role equals to UPDATED_ROLE
        defaultAdminAccountShouldNotBeFound("role.in=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void getAllAdminAccountsByRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        adminAccountRepository.saveAndFlush(adminAccount);

        // Get all the adminAccountList where role is not null
        defaultAdminAccountShouldBeFound("role.specified=true");

        // Get all the adminAccountList where role is null
        defaultAdminAccountShouldNotBeFound("role.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdminAccountsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = adminAccount.getUser();
        adminAccountRepository.saveAndFlush(adminAccount);
        Long userId = user.getId();

        // Get all the adminAccountList where user equals to userId
        defaultAdminAccountShouldBeFound("userId.equals=" + userId);

        // Get all the adminAccountList where user equals to userId + 1
        defaultAdminAccountShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdminAccountShouldBeFound(String filter) throws Exception {
        restAdminAccountMockMvc.perform(get("/api/admin-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));

        // Check, that the count call also returns 1
        restAdminAccountMockMvc.perform(get("/api/admin-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdminAccountShouldNotBeFound(String filter) throws Exception {
        restAdminAccountMockMvc.perform(get("/api/admin-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdminAccountMockMvc.perform(get("/api/admin-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdminAccount() throws Exception {
        // Get the adminAccount
        restAdminAccountMockMvc.perform(get("/api/admin-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdminAccount() throws Exception {
        // Initialize the database
        adminAccountRepository.saveAndFlush(adminAccount);

        int databaseSizeBeforeUpdate = adminAccountRepository.findAll().size();

        // Update the adminAccount
        AdminAccount updatedAdminAccount = adminAccountRepository.findById(adminAccount.getId()).get();
        // Disconnect from session so that the updates on updatedAdminAccount are not directly saved in db
        em.detach(updatedAdminAccount);
        updatedAdminAccount
            .role(UPDATED_ROLE);
        AdminAccountDTO adminAccountDTO = adminAccountMapper.toDto(updatedAdminAccount);

        restAdminAccountMockMvc.perform(put("/api/admin-accounts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adminAccountDTO)))
            .andExpect(status().isOk());

        // Validate the AdminAccount in the database
        List<AdminAccount> adminAccountList = adminAccountRepository.findAll();
        assertThat(adminAccountList).hasSize(databaseSizeBeforeUpdate);
        AdminAccount testAdminAccount = adminAccountList.get(adminAccountList.size() - 1);
        assertThat(testAdminAccount.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdminAccount() throws Exception {
        int databaseSizeBeforeUpdate = adminAccountRepository.findAll().size();

        // Create the AdminAccount
        AdminAccountDTO adminAccountDTO = adminAccountMapper.toDto(adminAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminAccountMockMvc.perform(put("/api/admin-accounts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adminAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdminAccount in the database
        List<AdminAccount> adminAccountList = adminAccountRepository.findAll();
        assertThat(adminAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdminAccount() throws Exception {
        // Initialize the database
        adminAccountRepository.saveAndFlush(adminAccount);

        int databaseSizeBeforeDelete = adminAccountRepository.findAll().size();

        // Delete the adminAccount
        restAdminAccountMockMvc.perform(delete("/api/admin-accounts/{id}", adminAccount.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminAccount> adminAccountList = adminAccountRepository.findAll();
        assertThat(adminAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
