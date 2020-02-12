package com.abrid.dropme.web.rest;

import com.abrid.dropme.DropMeApp;
import com.abrid.dropme.domain.Truck;
import com.abrid.dropme.domain.Driver;
import com.abrid.dropme.domain.Trip;
import com.abrid.dropme.domain.Conversation;
import com.abrid.dropme.domain.TransporterAccount;
import com.abrid.dropme.repository.TruckRepository;
import com.abrid.dropme.service.TruckService;
import com.abrid.dropme.service.dto.TruckDTO;
import com.abrid.dropme.service.mapper.TruckMapper;
import com.abrid.dropme.web.rest.errors.ExceptionTranslator;
import com.abrid.dropme.service.dto.TruckCriteria;
import com.abrid.dropme.service.TruckQueryService;

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

import com.abrid.dropme.domain.enumeration.ETruckType;
/**
 * Integration tests for the {@link TruckResource} REST controller.
 */
@SpringBootTest(classes = DropMeApp.class)
public class TruckResourceIT {

    private static final String DEFAULT_PLATE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PLATE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENEUR_PLATE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTENEUR_PLATE_NUMBER = "BBBBBBBBBB";

    private static final ETruckType DEFAULT_TYPE = ETruckType.DOMESTIC;
    private static final ETruckType UPDATED_TYPE = ETruckType.EXPORT;

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;
    private static final Integer SMALLER_WIDTH = 1 - 1;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;
    private static final Integer SMALLER_HEIGHT = 1 - 1;

    private static final Integer DEFAULT_LENGTH = 1;
    private static final Integer UPDATED_LENGTH = 2;
    private static final Integer SMALLER_LENGTH = 1 - 1;

    private static final Integer DEFAULT_MAX_WEIGHT = 1;
    private static final Integer UPDATED_MAX_WEIGHT = 2;
    private static final Integer SMALLER_MAX_WEIGHT = 1 - 1;

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private TruckMapper truckMapper;

    @Autowired
    private TruckService truckService;

    @Autowired
    private TruckQueryService truckQueryService;

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

    private MockMvc restTruckMockMvc;

    private Truck truck;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TruckResource truckResource = new TruckResource(truckService, truckQueryService);
        this.restTruckMockMvc = MockMvcBuilders.standaloneSetup(truckResource)
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
    public static Truck createEntity(EntityManager em) {
        Truck truck = new Truck()
            .plateNumber(DEFAULT_PLATE_NUMBER)
            .conteneurPlateNumber(DEFAULT_CONTENEUR_PLATE_NUMBER)
            .type(DEFAULT_TYPE)
            .width(DEFAULT_WIDTH)
            .height(DEFAULT_HEIGHT)
            .length(DEFAULT_LENGTH)
            .maxWeight(DEFAULT_MAX_WEIGHT);
        return truck;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Truck createUpdatedEntity(EntityManager em) {
        Truck truck = new Truck()
            .plateNumber(UPDATED_PLATE_NUMBER)
            .conteneurPlateNumber(UPDATED_CONTENEUR_PLATE_NUMBER)
            .type(UPDATED_TYPE)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .length(UPDATED_LENGTH)
            .maxWeight(UPDATED_MAX_WEIGHT);
        return truck;
    }

    @BeforeEach
    public void initTest() {
        truck = createEntity(em);
    }

    @Test
    @Transactional
    public void createTruck() throws Exception {
        int databaseSizeBeforeCreate = truckRepository.findAll().size();

        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);
        restTruckMockMvc.perform(post("/api/trucks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(truckDTO)))
            .andExpect(status().isCreated());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeCreate + 1);
        Truck testTruck = truckList.get(truckList.size() - 1);
        assertThat(testTruck.getPlateNumber()).isEqualTo(DEFAULT_PLATE_NUMBER);
        assertThat(testTruck.getConteneurPlateNumber()).isEqualTo(DEFAULT_CONTENEUR_PLATE_NUMBER);
        assertThat(testTruck.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTruck.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testTruck.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testTruck.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testTruck.getMaxWeight()).isEqualTo(DEFAULT_MAX_WEIGHT);
    }

    @Test
    @Transactional
    public void createTruckWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = truckRepository.findAll().size();

        // Create the Truck with an existing ID
        truck.setId(1L);
        TruckDTO truckDTO = truckMapper.toDto(truck);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTruckMockMvc.perform(post("/api/trucks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(truckDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTrucks() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList
        restTruckMockMvc.perform(get("/api/trucks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(truck.getId().intValue())))
            .andExpect(jsonPath("$.[*].plateNumber").value(hasItem(DEFAULT_PLATE_NUMBER)))
            .andExpect(jsonPath("$.[*].conteneurPlateNumber").value(hasItem(DEFAULT_CONTENEUR_PLATE_NUMBER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].maxWeight").value(hasItem(DEFAULT_MAX_WEIGHT)));
    }
    
    @Test
    @Transactional
    public void getTruck() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get the truck
        restTruckMockMvc.perform(get("/api/trucks/{id}", truck.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(truck.getId().intValue()))
            .andExpect(jsonPath("$.plateNumber").value(DEFAULT_PLATE_NUMBER))
            .andExpect(jsonPath("$.conteneurPlateNumber").value(DEFAULT_CONTENEUR_PLATE_NUMBER))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH))
            .andExpect(jsonPath("$.maxWeight").value(DEFAULT_MAX_WEIGHT));
    }


    @Test
    @Transactional
    public void getTrucksByIdFiltering() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        Long id = truck.getId();

        defaultTruckShouldBeFound("id.equals=" + id);
        defaultTruckShouldNotBeFound("id.notEquals=" + id);

        defaultTruckShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTruckShouldNotBeFound("id.greaterThan=" + id);

        defaultTruckShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTruckShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTrucksByPlateNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where plateNumber equals to DEFAULT_PLATE_NUMBER
        defaultTruckShouldBeFound("plateNumber.equals=" + DEFAULT_PLATE_NUMBER);

        // Get all the truckList where plateNumber equals to UPDATED_PLATE_NUMBER
        defaultTruckShouldNotBeFound("plateNumber.equals=" + UPDATED_PLATE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTrucksByPlateNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where plateNumber not equals to DEFAULT_PLATE_NUMBER
        defaultTruckShouldNotBeFound("plateNumber.notEquals=" + DEFAULT_PLATE_NUMBER);

        // Get all the truckList where plateNumber not equals to UPDATED_PLATE_NUMBER
        defaultTruckShouldBeFound("plateNumber.notEquals=" + UPDATED_PLATE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTrucksByPlateNumberIsInShouldWork() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where plateNumber in DEFAULT_PLATE_NUMBER or UPDATED_PLATE_NUMBER
        defaultTruckShouldBeFound("plateNumber.in=" + DEFAULT_PLATE_NUMBER + "," + UPDATED_PLATE_NUMBER);

        // Get all the truckList where plateNumber equals to UPDATED_PLATE_NUMBER
        defaultTruckShouldNotBeFound("plateNumber.in=" + UPDATED_PLATE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTrucksByPlateNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where plateNumber is not null
        defaultTruckShouldBeFound("plateNumber.specified=true");

        // Get all the truckList where plateNumber is null
        defaultTruckShouldNotBeFound("plateNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllTrucksByPlateNumberContainsSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where plateNumber contains DEFAULT_PLATE_NUMBER
        defaultTruckShouldBeFound("plateNumber.contains=" + DEFAULT_PLATE_NUMBER);

        // Get all the truckList where plateNumber contains UPDATED_PLATE_NUMBER
        defaultTruckShouldNotBeFound("plateNumber.contains=" + UPDATED_PLATE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTrucksByPlateNumberNotContainsSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where plateNumber does not contain DEFAULT_PLATE_NUMBER
        defaultTruckShouldNotBeFound("plateNumber.doesNotContain=" + DEFAULT_PLATE_NUMBER);

        // Get all the truckList where plateNumber does not contain UPDATED_PLATE_NUMBER
        defaultTruckShouldBeFound("plateNumber.doesNotContain=" + UPDATED_PLATE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllTrucksByConteneurPlateNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where conteneurPlateNumber equals to DEFAULT_CONTENEUR_PLATE_NUMBER
        defaultTruckShouldBeFound("conteneurPlateNumber.equals=" + DEFAULT_CONTENEUR_PLATE_NUMBER);

        // Get all the truckList where conteneurPlateNumber equals to UPDATED_CONTENEUR_PLATE_NUMBER
        defaultTruckShouldNotBeFound("conteneurPlateNumber.equals=" + UPDATED_CONTENEUR_PLATE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTrucksByConteneurPlateNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where conteneurPlateNumber not equals to DEFAULT_CONTENEUR_PLATE_NUMBER
        defaultTruckShouldNotBeFound("conteneurPlateNumber.notEquals=" + DEFAULT_CONTENEUR_PLATE_NUMBER);

        // Get all the truckList where conteneurPlateNumber not equals to UPDATED_CONTENEUR_PLATE_NUMBER
        defaultTruckShouldBeFound("conteneurPlateNumber.notEquals=" + UPDATED_CONTENEUR_PLATE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTrucksByConteneurPlateNumberIsInShouldWork() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where conteneurPlateNumber in DEFAULT_CONTENEUR_PLATE_NUMBER or UPDATED_CONTENEUR_PLATE_NUMBER
        defaultTruckShouldBeFound("conteneurPlateNumber.in=" + DEFAULT_CONTENEUR_PLATE_NUMBER + "," + UPDATED_CONTENEUR_PLATE_NUMBER);

        // Get all the truckList where conteneurPlateNumber equals to UPDATED_CONTENEUR_PLATE_NUMBER
        defaultTruckShouldNotBeFound("conteneurPlateNumber.in=" + UPDATED_CONTENEUR_PLATE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTrucksByConteneurPlateNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where conteneurPlateNumber is not null
        defaultTruckShouldBeFound("conteneurPlateNumber.specified=true");

        // Get all the truckList where conteneurPlateNumber is null
        defaultTruckShouldNotBeFound("conteneurPlateNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllTrucksByConteneurPlateNumberContainsSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where conteneurPlateNumber contains DEFAULT_CONTENEUR_PLATE_NUMBER
        defaultTruckShouldBeFound("conteneurPlateNumber.contains=" + DEFAULT_CONTENEUR_PLATE_NUMBER);

        // Get all the truckList where conteneurPlateNumber contains UPDATED_CONTENEUR_PLATE_NUMBER
        defaultTruckShouldNotBeFound("conteneurPlateNumber.contains=" + UPDATED_CONTENEUR_PLATE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTrucksByConteneurPlateNumberNotContainsSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where conteneurPlateNumber does not contain DEFAULT_CONTENEUR_PLATE_NUMBER
        defaultTruckShouldNotBeFound("conteneurPlateNumber.doesNotContain=" + DEFAULT_CONTENEUR_PLATE_NUMBER);

        // Get all the truckList where conteneurPlateNumber does not contain UPDATED_CONTENEUR_PLATE_NUMBER
        defaultTruckShouldBeFound("conteneurPlateNumber.doesNotContain=" + UPDATED_CONTENEUR_PLATE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllTrucksByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where type equals to DEFAULT_TYPE
        defaultTruckShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the truckList where type equals to UPDATED_TYPE
        defaultTruckShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTrucksByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where type not equals to DEFAULT_TYPE
        defaultTruckShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the truckList where type not equals to UPDATED_TYPE
        defaultTruckShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTrucksByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultTruckShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the truckList where type equals to UPDATED_TYPE
        defaultTruckShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTrucksByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where type is not null
        defaultTruckShouldBeFound("type.specified=true");

        // Get all the truckList where type is null
        defaultTruckShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrucksByWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where width equals to DEFAULT_WIDTH
        defaultTruckShouldBeFound("width.equals=" + DEFAULT_WIDTH);

        // Get all the truckList where width equals to UPDATED_WIDTH
        defaultTruckShouldNotBeFound("width.equals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllTrucksByWidthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where width not equals to DEFAULT_WIDTH
        defaultTruckShouldNotBeFound("width.notEquals=" + DEFAULT_WIDTH);

        // Get all the truckList where width not equals to UPDATED_WIDTH
        defaultTruckShouldBeFound("width.notEquals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllTrucksByWidthIsInShouldWork() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where width in DEFAULT_WIDTH or UPDATED_WIDTH
        defaultTruckShouldBeFound("width.in=" + DEFAULT_WIDTH + "," + UPDATED_WIDTH);

        // Get all the truckList where width equals to UPDATED_WIDTH
        defaultTruckShouldNotBeFound("width.in=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllTrucksByWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where width is not null
        defaultTruckShouldBeFound("width.specified=true");

        // Get all the truckList where width is null
        defaultTruckShouldNotBeFound("width.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrucksByWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where width is greater than or equal to DEFAULT_WIDTH
        defaultTruckShouldBeFound("width.greaterThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the truckList where width is greater than or equal to UPDATED_WIDTH
        defaultTruckShouldNotBeFound("width.greaterThanOrEqual=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllTrucksByWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where width is less than or equal to DEFAULT_WIDTH
        defaultTruckShouldBeFound("width.lessThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the truckList where width is less than or equal to SMALLER_WIDTH
        defaultTruckShouldNotBeFound("width.lessThanOrEqual=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    public void getAllTrucksByWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where width is less than DEFAULT_WIDTH
        defaultTruckShouldNotBeFound("width.lessThan=" + DEFAULT_WIDTH);

        // Get all the truckList where width is less than UPDATED_WIDTH
        defaultTruckShouldBeFound("width.lessThan=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllTrucksByWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where width is greater than DEFAULT_WIDTH
        defaultTruckShouldNotBeFound("width.greaterThan=" + DEFAULT_WIDTH);

        // Get all the truckList where width is greater than SMALLER_WIDTH
        defaultTruckShouldBeFound("width.greaterThan=" + SMALLER_WIDTH);
    }


    @Test
    @Transactional
    public void getAllTrucksByHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where height equals to DEFAULT_HEIGHT
        defaultTruckShouldBeFound("height.equals=" + DEFAULT_HEIGHT);

        // Get all the truckList where height equals to UPDATED_HEIGHT
        defaultTruckShouldNotBeFound("height.equals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllTrucksByHeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where height not equals to DEFAULT_HEIGHT
        defaultTruckShouldNotBeFound("height.notEquals=" + DEFAULT_HEIGHT);

        // Get all the truckList where height not equals to UPDATED_HEIGHT
        defaultTruckShouldBeFound("height.notEquals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllTrucksByHeightIsInShouldWork() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where height in DEFAULT_HEIGHT or UPDATED_HEIGHT
        defaultTruckShouldBeFound("height.in=" + DEFAULT_HEIGHT + "," + UPDATED_HEIGHT);

        // Get all the truckList where height equals to UPDATED_HEIGHT
        defaultTruckShouldNotBeFound("height.in=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllTrucksByHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where height is not null
        defaultTruckShouldBeFound("height.specified=true");

        // Get all the truckList where height is null
        defaultTruckShouldNotBeFound("height.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrucksByHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where height is greater than or equal to DEFAULT_HEIGHT
        defaultTruckShouldBeFound("height.greaterThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the truckList where height is greater than or equal to UPDATED_HEIGHT
        defaultTruckShouldNotBeFound("height.greaterThanOrEqual=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllTrucksByHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where height is less than or equal to DEFAULT_HEIGHT
        defaultTruckShouldBeFound("height.lessThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the truckList where height is less than or equal to SMALLER_HEIGHT
        defaultTruckShouldNotBeFound("height.lessThanOrEqual=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllTrucksByHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where height is less than DEFAULT_HEIGHT
        defaultTruckShouldNotBeFound("height.lessThan=" + DEFAULT_HEIGHT);

        // Get all the truckList where height is less than UPDATED_HEIGHT
        defaultTruckShouldBeFound("height.lessThan=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllTrucksByHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where height is greater than DEFAULT_HEIGHT
        defaultTruckShouldNotBeFound("height.greaterThan=" + DEFAULT_HEIGHT);

        // Get all the truckList where height is greater than SMALLER_HEIGHT
        defaultTruckShouldBeFound("height.greaterThan=" + SMALLER_HEIGHT);
    }


    @Test
    @Transactional
    public void getAllTrucksByLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where length equals to DEFAULT_LENGTH
        defaultTruckShouldBeFound("length.equals=" + DEFAULT_LENGTH);

        // Get all the truckList where length equals to UPDATED_LENGTH
        defaultTruckShouldNotBeFound("length.equals=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllTrucksByLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where length not equals to DEFAULT_LENGTH
        defaultTruckShouldNotBeFound("length.notEquals=" + DEFAULT_LENGTH);

        // Get all the truckList where length not equals to UPDATED_LENGTH
        defaultTruckShouldBeFound("length.notEquals=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllTrucksByLengthIsInShouldWork() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where length in DEFAULT_LENGTH or UPDATED_LENGTH
        defaultTruckShouldBeFound("length.in=" + DEFAULT_LENGTH + "," + UPDATED_LENGTH);

        // Get all the truckList where length equals to UPDATED_LENGTH
        defaultTruckShouldNotBeFound("length.in=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllTrucksByLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where length is not null
        defaultTruckShouldBeFound("length.specified=true");

        // Get all the truckList where length is null
        defaultTruckShouldNotBeFound("length.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrucksByLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where length is greater than or equal to DEFAULT_LENGTH
        defaultTruckShouldBeFound("length.greaterThanOrEqual=" + DEFAULT_LENGTH);

        // Get all the truckList where length is greater than or equal to UPDATED_LENGTH
        defaultTruckShouldNotBeFound("length.greaterThanOrEqual=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllTrucksByLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where length is less than or equal to DEFAULT_LENGTH
        defaultTruckShouldBeFound("length.lessThanOrEqual=" + DEFAULT_LENGTH);

        // Get all the truckList where length is less than or equal to SMALLER_LENGTH
        defaultTruckShouldNotBeFound("length.lessThanOrEqual=" + SMALLER_LENGTH);
    }

    @Test
    @Transactional
    public void getAllTrucksByLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where length is less than DEFAULT_LENGTH
        defaultTruckShouldNotBeFound("length.lessThan=" + DEFAULT_LENGTH);

        // Get all the truckList where length is less than UPDATED_LENGTH
        defaultTruckShouldBeFound("length.lessThan=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllTrucksByLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where length is greater than DEFAULT_LENGTH
        defaultTruckShouldNotBeFound("length.greaterThan=" + DEFAULT_LENGTH);

        // Get all the truckList where length is greater than SMALLER_LENGTH
        defaultTruckShouldBeFound("length.greaterThan=" + SMALLER_LENGTH);
    }


    @Test
    @Transactional
    public void getAllTrucksByMaxWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where maxWeight equals to DEFAULT_MAX_WEIGHT
        defaultTruckShouldBeFound("maxWeight.equals=" + DEFAULT_MAX_WEIGHT);

        // Get all the truckList where maxWeight equals to UPDATED_MAX_WEIGHT
        defaultTruckShouldNotBeFound("maxWeight.equals=" + UPDATED_MAX_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllTrucksByMaxWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where maxWeight not equals to DEFAULT_MAX_WEIGHT
        defaultTruckShouldNotBeFound("maxWeight.notEquals=" + DEFAULT_MAX_WEIGHT);

        // Get all the truckList where maxWeight not equals to UPDATED_MAX_WEIGHT
        defaultTruckShouldBeFound("maxWeight.notEquals=" + UPDATED_MAX_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllTrucksByMaxWeightIsInShouldWork() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where maxWeight in DEFAULT_MAX_WEIGHT or UPDATED_MAX_WEIGHT
        defaultTruckShouldBeFound("maxWeight.in=" + DEFAULT_MAX_WEIGHT + "," + UPDATED_MAX_WEIGHT);

        // Get all the truckList where maxWeight equals to UPDATED_MAX_WEIGHT
        defaultTruckShouldNotBeFound("maxWeight.in=" + UPDATED_MAX_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllTrucksByMaxWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where maxWeight is not null
        defaultTruckShouldBeFound("maxWeight.specified=true");

        // Get all the truckList where maxWeight is null
        defaultTruckShouldNotBeFound("maxWeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrucksByMaxWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where maxWeight is greater than or equal to DEFAULT_MAX_WEIGHT
        defaultTruckShouldBeFound("maxWeight.greaterThanOrEqual=" + DEFAULT_MAX_WEIGHT);

        // Get all the truckList where maxWeight is greater than or equal to UPDATED_MAX_WEIGHT
        defaultTruckShouldNotBeFound("maxWeight.greaterThanOrEqual=" + UPDATED_MAX_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllTrucksByMaxWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where maxWeight is less than or equal to DEFAULT_MAX_WEIGHT
        defaultTruckShouldBeFound("maxWeight.lessThanOrEqual=" + DEFAULT_MAX_WEIGHT);

        // Get all the truckList where maxWeight is less than or equal to SMALLER_MAX_WEIGHT
        defaultTruckShouldNotBeFound("maxWeight.lessThanOrEqual=" + SMALLER_MAX_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllTrucksByMaxWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where maxWeight is less than DEFAULT_MAX_WEIGHT
        defaultTruckShouldNotBeFound("maxWeight.lessThan=" + DEFAULT_MAX_WEIGHT);

        // Get all the truckList where maxWeight is less than UPDATED_MAX_WEIGHT
        defaultTruckShouldBeFound("maxWeight.lessThan=" + UPDATED_MAX_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllTrucksByMaxWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList where maxWeight is greater than DEFAULT_MAX_WEIGHT
        defaultTruckShouldNotBeFound("maxWeight.greaterThan=" + DEFAULT_MAX_WEIGHT);

        // Get all the truckList where maxWeight is greater than SMALLER_MAX_WEIGHT
        defaultTruckShouldBeFound("maxWeight.greaterThan=" + SMALLER_MAX_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllTrucksByDriverIsEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);
        Driver driver = DriverResourceIT.createEntity(em);
        em.persist(driver);
        em.flush();
        truck.setDriver(driver);
        truckRepository.saveAndFlush(truck);
        Long driverId = driver.getId();

        // Get all the truckList where driver equals to driverId
        defaultTruckShouldBeFound("driverId.equals=" + driverId);

        // Get all the truckList where driver equals to driverId + 1
        defaultTruckShouldNotBeFound("driverId.equals=" + (driverId + 1));
    }


    @Test
    @Transactional
    public void getAllTrucksByTripIsEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);
        Trip trip = TripResourceIT.createEntity(em);
        em.persist(trip);
        em.flush();
        truck.setTrip(trip);
        truckRepository.saveAndFlush(truck);
        Long tripId = trip.getId();

        // Get all the truckList where trip equals to tripId
        defaultTruckShouldBeFound("tripId.equals=" + tripId);

        // Get all the truckList where trip equals to tripId + 1
        defaultTruckShouldNotBeFound("tripId.equals=" + (tripId + 1));
    }


    @Test
    @Transactional
    public void getAllTrucksByConversationIsEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);
        Conversation conversation = ConversationResourceIT.createEntity(em);
        em.persist(conversation);
        em.flush();
        truck.addConversation(conversation);
        truckRepository.saveAndFlush(truck);
        Long conversationId = conversation.getId();

        // Get all the truckList where conversation equals to conversationId
        defaultTruckShouldBeFound("conversationId.equals=" + conversationId);

        // Get all the truckList where conversation equals to conversationId + 1
        defaultTruckShouldNotBeFound("conversationId.equals=" + (conversationId + 1));
    }


    @Test
    @Transactional
    public void getAllTrucksByTransporterAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);
        TransporterAccount transporterAccount = TransporterAccountResourceIT.createEntity(em);
        em.persist(transporterAccount);
        em.flush();
        truck.setTransporterAccount(transporterAccount);
        truckRepository.saveAndFlush(truck);
        Long transporterAccountId = transporterAccount.getId();

        // Get all the truckList where transporterAccount equals to transporterAccountId
        defaultTruckShouldBeFound("transporterAccountId.equals=" + transporterAccountId);

        // Get all the truckList where transporterAccount equals to transporterAccountId + 1
        defaultTruckShouldNotBeFound("transporterAccountId.equals=" + (transporterAccountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTruckShouldBeFound(String filter) throws Exception {
        restTruckMockMvc.perform(get("/api/trucks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(truck.getId().intValue())))
            .andExpect(jsonPath("$.[*].plateNumber").value(hasItem(DEFAULT_PLATE_NUMBER)))
            .andExpect(jsonPath("$.[*].conteneurPlateNumber").value(hasItem(DEFAULT_CONTENEUR_PLATE_NUMBER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].maxWeight").value(hasItem(DEFAULT_MAX_WEIGHT)));

        // Check, that the count call also returns 1
        restTruckMockMvc.perform(get("/api/trucks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTruckShouldNotBeFound(String filter) throws Exception {
        restTruckMockMvc.perform(get("/api/trucks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTruckMockMvc.perform(get("/api/trucks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTruck() throws Exception {
        // Get the truck
        restTruckMockMvc.perform(get("/api/trucks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTruck() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        int databaseSizeBeforeUpdate = truckRepository.findAll().size();

        // Update the truck
        Truck updatedTruck = truckRepository.findById(truck.getId()).get();
        // Disconnect from session so that the updates on updatedTruck are not directly saved in db
        em.detach(updatedTruck);
        updatedTruck
            .plateNumber(UPDATED_PLATE_NUMBER)
            .conteneurPlateNumber(UPDATED_CONTENEUR_PLATE_NUMBER)
            .type(UPDATED_TYPE)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .length(UPDATED_LENGTH)
            .maxWeight(UPDATED_MAX_WEIGHT);
        TruckDTO truckDTO = truckMapper.toDto(updatedTruck);

        restTruckMockMvc.perform(put("/api/trucks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(truckDTO)))
            .andExpect(status().isOk());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeUpdate);
        Truck testTruck = truckList.get(truckList.size() - 1);
        assertThat(testTruck.getPlateNumber()).isEqualTo(UPDATED_PLATE_NUMBER);
        assertThat(testTruck.getConteneurPlateNumber()).isEqualTo(UPDATED_CONTENEUR_PLATE_NUMBER);
        assertThat(testTruck.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTruck.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testTruck.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testTruck.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testTruck.getMaxWeight()).isEqualTo(UPDATED_MAX_WEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingTruck() throws Exception {
        int databaseSizeBeforeUpdate = truckRepository.findAll().size();

        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTruckMockMvc.perform(put("/api/trucks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(truckDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTruck() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        int databaseSizeBeforeDelete = truckRepository.findAll().size();

        // Delete the truck
        restTruckMockMvc.perform(delete("/api/trucks/{id}", truck.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
