package com.abrid.dropme.web.rest;

import com.abrid.dropme.DropMeApp;
import com.abrid.dropme.domain.Driver;
import com.abrid.dropme.domain.Truck;
import com.abrid.dropme.repository.DriverRepository;
import com.abrid.dropme.service.DriverService;
import com.abrid.dropme.service.dto.DriverDTO;
import com.abrid.dropme.service.mapper.DriverMapper;
import com.abrid.dropme.web.rest.errors.ExceptionTranslator;
import com.abrid.dropme.service.dto.DriverCriteria;
import com.abrid.dropme.service.DriverQueryService;

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
 * Integration tests for the {@link DriverResource} REST controller.
 */
@SpringBootTest(classes = DropMeApp.class)
public class DriverResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_COORDINATE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_COORDINATE = "BBBBBBBBBB";

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverMapper driverMapper;

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverQueryService driverQueryService;

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

    private MockMvc restDriverMockMvc;

    private Driver driver;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DriverResource driverResource = new DriverResource(driverService, driverQueryService);
        this.restDriverMockMvc = MockMvcBuilders.standaloneSetup(driverResource)
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
    public static Driver createEntity(EntityManager em) {
        Driver driver = new Driver()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .currentCoordinate(DEFAULT_CURRENT_COORDINATE);
        return driver;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Driver createUpdatedEntity(EntityManager em) {
        Driver driver = new Driver()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .currentCoordinate(UPDATED_CURRENT_COORDINATE);
        return driver;
    }

    @BeforeEach
    public void initTest() {
        driver = createEntity(em);
    }

    @Test
    @Transactional
    public void createDriver() throws Exception {
        int databaseSizeBeforeCreate = driverRepository.findAll().size();

        // Create the Driver
        DriverDTO driverDTO = driverMapper.toDto(driver);
        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isCreated());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeCreate + 1);
        Driver testDriver = driverList.get(driverList.size() - 1);
        assertThat(testDriver.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testDriver.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testDriver.getCurrentCoordinate()).isEqualTo(DEFAULT_CURRENT_COORDINATE);
    }

    @Test
    @Transactional
    public void createDriverWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = driverRepository.findAll().size();

        // Create the Driver with an existing ID
        driver.setId(1L);
        DriverDTO driverDTO = driverMapper.toDto(driver);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDrivers() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList
        restDriverMockMvc.perform(get("/api/drivers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(driver.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].currentCoordinate").value(hasItem(DEFAULT_CURRENT_COORDINATE)));
    }
    
    @Test
    @Transactional
    public void getDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", driver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(driver.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.currentCoordinate").value(DEFAULT_CURRENT_COORDINATE));
    }


    @Test
    @Transactional
    public void getDriversByIdFiltering() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        Long id = driver.getId();

        defaultDriverShouldBeFound("id.equals=" + id);
        defaultDriverShouldNotBeFound("id.notEquals=" + id);

        defaultDriverShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDriverShouldNotBeFound("id.greaterThan=" + id);

        defaultDriverShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDriverShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDriversByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where firstName equals to DEFAULT_FIRST_NAME
        defaultDriverShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the driverList where firstName equals to UPDATED_FIRST_NAME
        defaultDriverShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllDriversByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where firstName not equals to DEFAULT_FIRST_NAME
        defaultDriverShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the driverList where firstName not equals to UPDATED_FIRST_NAME
        defaultDriverShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllDriversByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultDriverShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the driverList where firstName equals to UPDATED_FIRST_NAME
        defaultDriverShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllDriversByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where firstName is not null
        defaultDriverShouldBeFound("firstName.specified=true");

        // Get all the driverList where firstName is null
        defaultDriverShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllDriversByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where firstName contains DEFAULT_FIRST_NAME
        defaultDriverShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the driverList where firstName contains UPDATED_FIRST_NAME
        defaultDriverShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllDriversByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where firstName does not contain DEFAULT_FIRST_NAME
        defaultDriverShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the driverList where firstName does not contain UPDATED_FIRST_NAME
        defaultDriverShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllDriversByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where lastName equals to DEFAULT_LAST_NAME
        defaultDriverShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the driverList where lastName equals to UPDATED_LAST_NAME
        defaultDriverShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllDriversByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where lastName not equals to DEFAULT_LAST_NAME
        defaultDriverShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the driverList where lastName not equals to UPDATED_LAST_NAME
        defaultDriverShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllDriversByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultDriverShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the driverList where lastName equals to UPDATED_LAST_NAME
        defaultDriverShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllDriversByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where lastName is not null
        defaultDriverShouldBeFound("lastName.specified=true");

        // Get all the driverList where lastName is null
        defaultDriverShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllDriversByLastNameContainsSomething() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where lastName contains DEFAULT_LAST_NAME
        defaultDriverShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the driverList where lastName contains UPDATED_LAST_NAME
        defaultDriverShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllDriversByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where lastName does not contain DEFAULT_LAST_NAME
        defaultDriverShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the driverList where lastName does not contain UPDATED_LAST_NAME
        defaultDriverShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllDriversByCurrentCoordinateIsEqualToSomething() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where currentCoordinate equals to DEFAULT_CURRENT_COORDINATE
        defaultDriverShouldBeFound("currentCoordinate.equals=" + DEFAULT_CURRENT_COORDINATE);

        // Get all the driverList where currentCoordinate equals to UPDATED_CURRENT_COORDINATE
        defaultDriverShouldNotBeFound("currentCoordinate.equals=" + UPDATED_CURRENT_COORDINATE);
    }

    @Test
    @Transactional
    public void getAllDriversByCurrentCoordinateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where currentCoordinate not equals to DEFAULT_CURRENT_COORDINATE
        defaultDriverShouldNotBeFound("currentCoordinate.notEquals=" + DEFAULT_CURRENT_COORDINATE);

        // Get all the driverList where currentCoordinate not equals to UPDATED_CURRENT_COORDINATE
        defaultDriverShouldBeFound("currentCoordinate.notEquals=" + UPDATED_CURRENT_COORDINATE);
    }

    @Test
    @Transactional
    public void getAllDriversByCurrentCoordinateIsInShouldWork() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where currentCoordinate in DEFAULT_CURRENT_COORDINATE or UPDATED_CURRENT_COORDINATE
        defaultDriverShouldBeFound("currentCoordinate.in=" + DEFAULT_CURRENT_COORDINATE + "," + UPDATED_CURRENT_COORDINATE);

        // Get all the driverList where currentCoordinate equals to UPDATED_CURRENT_COORDINATE
        defaultDriverShouldNotBeFound("currentCoordinate.in=" + UPDATED_CURRENT_COORDINATE);
    }

    @Test
    @Transactional
    public void getAllDriversByCurrentCoordinateIsNullOrNotNull() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where currentCoordinate is not null
        defaultDriverShouldBeFound("currentCoordinate.specified=true");

        // Get all the driverList where currentCoordinate is null
        defaultDriverShouldNotBeFound("currentCoordinate.specified=false");
    }
                @Test
    @Transactional
    public void getAllDriversByCurrentCoordinateContainsSomething() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where currentCoordinate contains DEFAULT_CURRENT_COORDINATE
        defaultDriverShouldBeFound("currentCoordinate.contains=" + DEFAULT_CURRENT_COORDINATE);

        // Get all the driverList where currentCoordinate contains UPDATED_CURRENT_COORDINATE
        defaultDriverShouldNotBeFound("currentCoordinate.contains=" + UPDATED_CURRENT_COORDINATE);
    }

    @Test
    @Transactional
    public void getAllDriversByCurrentCoordinateNotContainsSomething() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList where currentCoordinate does not contain DEFAULT_CURRENT_COORDINATE
        defaultDriverShouldNotBeFound("currentCoordinate.doesNotContain=" + DEFAULT_CURRENT_COORDINATE);

        // Get all the driverList where currentCoordinate does not contain UPDATED_CURRENT_COORDINATE
        defaultDriverShouldBeFound("currentCoordinate.doesNotContain=" + UPDATED_CURRENT_COORDINATE);
    }


    @Test
    @Transactional
    public void getAllDriversByTruckIsEqualToSomething() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);
        Truck truck = TruckResourceIT.createEntity(em);
        em.persist(truck);
        em.flush();
        driver.setTruck(truck);
        truck.setDriver(driver);
        driverRepository.saveAndFlush(driver);
        Long truckId = truck.getId();

        // Get all the driverList where truck equals to truckId
        defaultDriverShouldBeFound("truckId.equals=" + truckId);

        // Get all the driverList where truck equals to truckId + 1
        defaultDriverShouldNotBeFound("truckId.equals=" + (truckId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDriverShouldBeFound(String filter) throws Exception {
        restDriverMockMvc.perform(get("/api/drivers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(driver.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].currentCoordinate").value(hasItem(DEFAULT_CURRENT_COORDINATE)));

        // Check, that the count call also returns 1
        restDriverMockMvc.perform(get("/api/drivers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDriverShouldNotBeFound(String filter) throws Exception {
        restDriverMockMvc.perform(get("/api/drivers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDriverMockMvc.perform(get("/api/drivers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDriver() throws Exception {
        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        int databaseSizeBeforeUpdate = driverRepository.findAll().size();

        // Update the driver
        Driver updatedDriver = driverRepository.findById(driver.getId()).get();
        // Disconnect from session so that the updates on updatedDriver are not directly saved in db
        em.detach(updatedDriver);
        updatedDriver
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .currentCoordinate(UPDATED_CURRENT_COORDINATE);
        DriverDTO driverDTO = driverMapper.toDto(updatedDriver);

        restDriverMockMvc.perform(put("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isOk());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeUpdate);
        Driver testDriver = driverList.get(driverList.size() - 1);
        assertThat(testDriver.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testDriver.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testDriver.getCurrentCoordinate()).isEqualTo(UPDATED_CURRENT_COORDINATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDriver() throws Exception {
        int databaseSizeBeforeUpdate = driverRepository.findAll().size();

        // Create the Driver
        DriverDTO driverDTO = driverMapper.toDto(driver);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDriverMockMvc.perform(put("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        int databaseSizeBeforeDelete = driverRepository.findAll().size();

        // Delete the driver
        restDriverMockMvc.perform(delete("/api/drivers/{id}", driver.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
