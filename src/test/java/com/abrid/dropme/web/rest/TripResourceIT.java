package com.abrid.dropme.web.rest;

import com.abrid.dropme.DropMeApp;
import com.abrid.dropme.domain.Trip;
import com.abrid.dropme.domain.Origin;
import com.abrid.dropme.domain.Destination;
import com.abrid.dropme.domain.Conversation;
import com.abrid.dropme.domain.Truck;
import com.abrid.dropme.domain.ClientAccount;
import com.abrid.dropme.repository.TripRepository;
import com.abrid.dropme.service.TripService;
import com.abrid.dropme.service.dto.TripDTO;
import com.abrid.dropme.service.mapper.TripMapper;
import com.abrid.dropme.web.rest.errors.ExceptionTranslator;
import com.abrid.dropme.service.dto.TripCriteria;
import com.abrid.dropme.service.TripQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.abrid.dropme.web.rest.TestUtil.sameInstant;
import static com.abrid.dropme.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.abrid.dropme.domain.enumeration.EMarchandise;
import com.abrid.dropme.domain.enumeration.ETripState;
/**
 * Integration tests for the {@link TripResource} REST controller.
 */
@SpringBootTest(classes = DropMeApp.class)
public class TripResourceIT {

    private static final Boolean DEFAULT_IS_FULL = false;
    private static final Boolean UPDATED_IS_FULL = true;

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;
    private static final Integer SMALLER_WIDTH = 1 - 1;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;
    private static final Integer SMALLER_HEIGHT = 1 - 1;

    private static final Integer DEFAULT_LENGTH = 1;
    private static final Integer UPDATED_LENGTH = 2;
    private static final Integer SMALLER_LENGTH = 1 - 1;

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;
    private static final Integer SMALLER_WEIGHT = 1 - 1;

    private static final EMarchandise DEFAULT_MARCHANDISE = EMarchandise.VEGETABLES;
    private static final EMarchandise UPDATED_MARCHANDISE = EMarchandise.RAW_MATERIAL;

    private static final ZonedDateTime DEFAULT_ETD = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ETD = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ETD = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ETripState DEFAULT_STATE = ETripState.FINISHED;
    private static final ETripState UPDATED_STATE = ETripState.ACTIVATED;

    private static final ZonedDateTime DEFAULT_ETA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ETA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ETA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Integer DEFAULT_DISTANCE = 1;
    private static final Integer UPDATED_DISTANCE = 2;
    private static final Integer SMALLER_DISTANCE = 1 - 1;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripMapper tripMapper;

    @Autowired
    private TripService tripService;

    @Autowired
    private TripQueryService tripQueryService;

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

    private MockMvc restTripMockMvc;

    private Trip trip;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TripResource tripResource = new TripResource(tripService, tripQueryService);
        this.restTripMockMvc = MockMvcBuilders.standaloneSetup(tripResource)
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
    public static Trip createEntity(EntityManager em) {
        Trip trip = new Trip()
            .isFull(DEFAULT_IS_FULL)
            .width(DEFAULT_WIDTH)
            .height(DEFAULT_HEIGHT)
            .length(DEFAULT_LENGTH)
            .weight(DEFAULT_WEIGHT)
            .marchandise(DEFAULT_MARCHANDISE)
            .etd(DEFAULT_ETD)
            .description(DEFAULT_DESCRIPTION)
            .state(DEFAULT_STATE)
            .eta(DEFAULT_ETA)
            .distance(DEFAULT_DISTANCE);
        return trip;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trip createUpdatedEntity(EntityManager em) {
        Trip trip = new Trip()
            .isFull(UPDATED_IS_FULL)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .length(UPDATED_LENGTH)
            .weight(UPDATED_WEIGHT)
            .marchandise(UPDATED_MARCHANDISE)
            .etd(UPDATED_ETD)
            .description(UPDATED_DESCRIPTION)
            .state(UPDATED_STATE)
            .eta(UPDATED_ETA)
            .distance(UPDATED_DISTANCE);
        return trip;
    }

    @BeforeEach
    public void initTest() {
        trip = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrip() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // Create the Trip
        TripDTO tripDTO = tripMapper.toDto(trip);
        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isCreated());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeCreate + 1);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.isIsFull()).isEqualTo(DEFAULT_IS_FULL);
        assertThat(testTrip.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testTrip.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testTrip.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testTrip.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testTrip.getMarchandise()).isEqualTo(DEFAULT_MARCHANDISE);
        assertThat(testTrip.getEtd()).isEqualTo(DEFAULT_ETD);
        assertThat(testTrip.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTrip.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testTrip.getEta()).isEqualTo(DEFAULT_ETA);
        assertThat(testTrip.getDistance()).isEqualTo(DEFAULT_DISTANCE);
    }

    @Test
    @Transactional
    public void createTripWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // Create the Trip with an existing ID
        trip.setId(1L);
        TripDTO tripDTO = tripMapper.toDto(trip);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIsFullIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setIsFull(null);

        // Create the Trip, which fails.
        TripDTO tripDTO = tripMapper.toDto(trip);

        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMarchandiseIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setMarchandise(null);

        // Create the Trip, which fails.
        TripDTO tripDTO = tripMapper.toDto(trip);

        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setEtd(null);

        // Create the Trip, which fails.
        TripDTO tripDTO = tripMapper.toDto(trip);

        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDistanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setDistance(null);

        // Create the Trip, which fails.
        TripDTO tripDTO = tripMapper.toDto(trip);

        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrips() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList
        restTripMockMvc.perform(get("/api/trips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trip.getId().intValue())))
            .andExpect(jsonPath("$.[*].isFull").value(hasItem(DEFAULT_IS_FULL.booleanValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].marchandise").value(hasItem(DEFAULT_MARCHANDISE.toString())))
            .andExpect(jsonPath("$.[*].etd").value(hasItem(sameInstant(DEFAULT_ETD))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].eta").value(hasItem(sameInstant(DEFAULT_ETA))))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE)));
    }
    
    @Test
    @Transactional
    public void getTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", trip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trip.getId().intValue()))
            .andExpect(jsonPath("$.isFull").value(DEFAULT_IS_FULL.booleanValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.marchandise").value(DEFAULT_MARCHANDISE.toString()))
            .andExpect(jsonPath("$.etd").value(sameInstant(DEFAULT_ETD)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.eta").value(sameInstant(DEFAULT_ETA)))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE));
    }


    @Test
    @Transactional
    public void getTripsByIdFiltering() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        Long id = trip.getId();

        defaultTripShouldBeFound("id.equals=" + id);
        defaultTripShouldNotBeFound("id.notEquals=" + id);

        defaultTripShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTripShouldNotBeFound("id.greaterThan=" + id);

        defaultTripShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTripShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTripsByIsFullIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where isFull equals to DEFAULT_IS_FULL
        defaultTripShouldBeFound("isFull.equals=" + DEFAULT_IS_FULL);

        // Get all the tripList where isFull equals to UPDATED_IS_FULL
        defaultTripShouldNotBeFound("isFull.equals=" + UPDATED_IS_FULL);
    }

    @Test
    @Transactional
    public void getAllTripsByIsFullIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where isFull not equals to DEFAULT_IS_FULL
        defaultTripShouldNotBeFound("isFull.notEquals=" + DEFAULT_IS_FULL);

        // Get all the tripList where isFull not equals to UPDATED_IS_FULL
        defaultTripShouldBeFound("isFull.notEquals=" + UPDATED_IS_FULL);
    }

    @Test
    @Transactional
    public void getAllTripsByIsFullIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where isFull in DEFAULT_IS_FULL or UPDATED_IS_FULL
        defaultTripShouldBeFound("isFull.in=" + DEFAULT_IS_FULL + "," + UPDATED_IS_FULL);

        // Get all the tripList where isFull equals to UPDATED_IS_FULL
        defaultTripShouldNotBeFound("isFull.in=" + UPDATED_IS_FULL);
    }

    @Test
    @Transactional
    public void getAllTripsByIsFullIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where isFull is not null
        defaultTripShouldBeFound("isFull.specified=true");

        // Get all the tripList where isFull is null
        defaultTripShouldNotBeFound("isFull.specified=false");
    }

    @Test
    @Transactional
    public void getAllTripsByWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where width equals to DEFAULT_WIDTH
        defaultTripShouldBeFound("width.equals=" + DEFAULT_WIDTH);

        // Get all the tripList where width equals to UPDATED_WIDTH
        defaultTripShouldNotBeFound("width.equals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllTripsByWidthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where width not equals to DEFAULT_WIDTH
        defaultTripShouldNotBeFound("width.notEquals=" + DEFAULT_WIDTH);

        // Get all the tripList where width not equals to UPDATED_WIDTH
        defaultTripShouldBeFound("width.notEquals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllTripsByWidthIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where width in DEFAULT_WIDTH or UPDATED_WIDTH
        defaultTripShouldBeFound("width.in=" + DEFAULT_WIDTH + "," + UPDATED_WIDTH);

        // Get all the tripList where width equals to UPDATED_WIDTH
        defaultTripShouldNotBeFound("width.in=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllTripsByWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where width is not null
        defaultTripShouldBeFound("width.specified=true");

        // Get all the tripList where width is null
        defaultTripShouldNotBeFound("width.specified=false");
    }

    @Test
    @Transactional
    public void getAllTripsByWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where width is greater than or equal to DEFAULT_WIDTH
        defaultTripShouldBeFound("width.greaterThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the tripList where width is greater than or equal to UPDATED_WIDTH
        defaultTripShouldNotBeFound("width.greaterThanOrEqual=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllTripsByWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where width is less than or equal to DEFAULT_WIDTH
        defaultTripShouldBeFound("width.lessThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the tripList where width is less than or equal to SMALLER_WIDTH
        defaultTripShouldNotBeFound("width.lessThanOrEqual=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    public void getAllTripsByWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where width is less than DEFAULT_WIDTH
        defaultTripShouldNotBeFound("width.lessThan=" + DEFAULT_WIDTH);

        // Get all the tripList where width is less than UPDATED_WIDTH
        defaultTripShouldBeFound("width.lessThan=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllTripsByWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where width is greater than DEFAULT_WIDTH
        defaultTripShouldNotBeFound("width.greaterThan=" + DEFAULT_WIDTH);

        // Get all the tripList where width is greater than SMALLER_WIDTH
        defaultTripShouldBeFound("width.greaterThan=" + SMALLER_WIDTH);
    }


    @Test
    @Transactional
    public void getAllTripsByHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where height equals to DEFAULT_HEIGHT
        defaultTripShouldBeFound("height.equals=" + DEFAULT_HEIGHT);

        // Get all the tripList where height equals to UPDATED_HEIGHT
        defaultTripShouldNotBeFound("height.equals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllTripsByHeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where height not equals to DEFAULT_HEIGHT
        defaultTripShouldNotBeFound("height.notEquals=" + DEFAULT_HEIGHT);

        // Get all the tripList where height not equals to UPDATED_HEIGHT
        defaultTripShouldBeFound("height.notEquals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllTripsByHeightIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where height in DEFAULT_HEIGHT or UPDATED_HEIGHT
        defaultTripShouldBeFound("height.in=" + DEFAULT_HEIGHT + "," + UPDATED_HEIGHT);

        // Get all the tripList where height equals to UPDATED_HEIGHT
        defaultTripShouldNotBeFound("height.in=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllTripsByHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where height is not null
        defaultTripShouldBeFound("height.specified=true");

        // Get all the tripList where height is null
        defaultTripShouldNotBeFound("height.specified=false");
    }

    @Test
    @Transactional
    public void getAllTripsByHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where height is greater than or equal to DEFAULT_HEIGHT
        defaultTripShouldBeFound("height.greaterThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the tripList where height is greater than or equal to UPDATED_HEIGHT
        defaultTripShouldNotBeFound("height.greaterThanOrEqual=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllTripsByHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where height is less than or equal to DEFAULT_HEIGHT
        defaultTripShouldBeFound("height.lessThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the tripList where height is less than or equal to SMALLER_HEIGHT
        defaultTripShouldNotBeFound("height.lessThanOrEqual=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllTripsByHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where height is less than DEFAULT_HEIGHT
        defaultTripShouldNotBeFound("height.lessThan=" + DEFAULT_HEIGHT);

        // Get all the tripList where height is less than UPDATED_HEIGHT
        defaultTripShouldBeFound("height.lessThan=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllTripsByHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where height is greater than DEFAULT_HEIGHT
        defaultTripShouldNotBeFound("height.greaterThan=" + DEFAULT_HEIGHT);

        // Get all the tripList where height is greater than SMALLER_HEIGHT
        defaultTripShouldBeFound("height.greaterThan=" + SMALLER_HEIGHT);
    }


    @Test
    @Transactional
    public void getAllTripsByLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where length equals to DEFAULT_LENGTH
        defaultTripShouldBeFound("length.equals=" + DEFAULT_LENGTH);

        // Get all the tripList where length equals to UPDATED_LENGTH
        defaultTripShouldNotBeFound("length.equals=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllTripsByLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where length not equals to DEFAULT_LENGTH
        defaultTripShouldNotBeFound("length.notEquals=" + DEFAULT_LENGTH);

        // Get all the tripList where length not equals to UPDATED_LENGTH
        defaultTripShouldBeFound("length.notEquals=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllTripsByLengthIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where length in DEFAULT_LENGTH or UPDATED_LENGTH
        defaultTripShouldBeFound("length.in=" + DEFAULT_LENGTH + "," + UPDATED_LENGTH);

        // Get all the tripList where length equals to UPDATED_LENGTH
        defaultTripShouldNotBeFound("length.in=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllTripsByLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where length is not null
        defaultTripShouldBeFound("length.specified=true");

        // Get all the tripList where length is null
        defaultTripShouldNotBeFound("length.specified=false");
    }

    @Test
    @Transactional
    public void getAllTripsByLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where length is greater than or equal to DEFAULT_LENGTH
        defaultTripShouldBeFound("length.greaterThanOrEqual=" + DEFAULT_LENGTH);

        // Get all the tripList where length is greater than or equal to UPDATED_LENGTH
        defaultTripShouldNotBeFound("length.greaterThanOrEqual=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllTripsByLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where length is less than or equal to DEFAULT_LENGTH
        defaultTripShouldBeFound("length.lessThanOrEqual=" + DEFAULT_LENGTH);

        // Get all the tripList where length is less than or equal to SMALLER_LENGTH
        defaultTripShouldNotBeFound("length.lessThanOrEqual=" + SMALLER_LENGTH);
    }

    @Test
    @Transactional
    public void getAllTripsByLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where length is less than DEFAULT_LENGTH
        defaultTripShouldNotBeFound("length.lessThan=" + DEFAULT_LENGTH);

        // Get all the tripList where length is less than UPDATED_LENGTH
        defaultTripShouldBeFound("length.lessThan=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllTripsByLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where length is greater than DEFAULT_LENGTH
        defaultTripShouldNotBeFound("length.greaterThan=" + DEFAULT_LENGTH);

        // Get all the tripList where length is greater than SMALLER_LENGTH
        defaultTripShouldBeFound("length.greaterThan=" + SMALLER_LENGTH);
    }


    @Test
    @Transactional
    public void getAllTripsByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where weight equals to DEFAULT_WEIGHT
        defaultTripShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the tripList where weight equals to UPDATED_WEIGHT
        defaultTripShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllTripsByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where weight not equals to DEFAULT_WEIGHT
        defaultTripShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the tripList where weight not equals to UPDATED_WEIGHT
        defaultTripShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllTripsByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultTripShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the tripList where weight equals to UPDATED_WEIGHT
        defaultTripShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllTripsByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where weight is not null
        defaultTripShouldBeFound("weight.specified=true");

        // Get all the tripList where weight is null
        defaultTripShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    public void getAllTripsByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultTripShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the tripList where weight is greater than or equal to UPDATED_WEIGHT
        defaultTripShouldNotBeFound("weight.greaterThanOrEqual=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllTripsByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where weight is less than or equal to DEFAULT_WEIGHT
        defaultTripShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the tripList where weight is less than or equal to SMALLER_WEIGHT
        defaultTripShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllTripsByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where weight is less than DEFAULT_WEIGHT
        defaultTripShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the tripList where weight is less than UPDATED_WEIGHT
        defaultTripShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllTripsByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where weight is greater than DEFAULT_WEIGHT
        defaultTripShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the tripList where weight is greater than SMALLER_WEIGHT
        defaultTripShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllTripsByMarchandiseIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where marchandise equals to DEFAULT_MARCHANDISE
        defaultTripShouldBeFound("marchandise.equals=" + DEFAULT_MARCHANDISE);

        // Get all the tripList where marchandise equals to UPDATED_MARCHANDISE
        defaultTripShouldNotBeFound("marchandise.equals=" + UPDATED_MARCHANDISE);
    }

    @Test
    @Transactional
    public void getAllTripsByMarchandiseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where marchandise not equals to DEFAULT_MARCHANDISE
        defaultTripShouldNotBeFound("marchandise.notEquals=" + DEFAULT_MARCHANDISE);

        // Get all the tripList where marchandise not equals to UPDATED_MARCHANDISE
        defaultTripShouldBeFound("marchandise.notEquals=" + UPDATED_MARCHANDISE);
    }

    @Test
    @Transactional
    public void getAllTripsByMarchandiseIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where marchandise in DEFAULT_MARCHANDISE or UPDATED_MARCHANDISE
        defaultTripShouldBeFound("marchandise.in=" + DEFAULT_MARCHANDISE + "," + UPDATED_MARCHANDISE);

        // Get all the tripList where marchandise equals to UPDATED_MARCHANDISE
        defaultTripShouldNotBeFound("marchandise.in=" + UPDATED_MARCHANDISE);
    }

    @Test
    @Transactional
    public void getAllTripsByMarchandiseIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where marchandise is not null
        defaultTripShouldBeFound("marchandise.specified=true");

        // Get all the tripList where marchandise is null
        defaultTripShouldNotBeFound("marchandise.specified=false");
    }

    @Test
    @Transactional
    public void getAllTripsByEtdIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where etd equals to DEFAULT_ETD
        defaultTripShouldBeFound("etd.equals=" + DEFAULT_ETD);

        // Get all the tripList where etd equals to UPDATED_ETD
        defaultTripShouldNotBeFound("etd.equals=" + UPDATED_ETD);
    }

    @Test
    @Transactional
    public void getAllTripsByEtdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where etd not equals to DEFAULT_ETD
        defaultTripShouldNotBeFound("etd.notEquals=" + DEFAULT_ETD);

        // Get all the tripList where etd not equals to UPDATED_ETD
        defaultTripShouldBeFound("etd.notEquals=" + UPDATED_ETD);
    }

    @Test
    @Transactional
    public void getAllTripsByEtdIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where etd in DEFAULT_ETD or UPDATED_ETD
        defaultTripShouldBeFound("etd.in=" + DEFAULT_ETD + "," + UPDATED_ETD);

        // Get all the tripList where etd equals to UPDATED_ETD
        defaultTripShouldNotBeFound("etd.in=" + UPDATED_ETD);
    }

    @Test
    @Transactional
    public void getAllTripsByEtdIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where etd is not null
        defaultTripShouldBeFound("etd.specified=true");

        // Get all the tripList where etd is null
        defaultTripShouldNotBeFound("etd.specified=false");
    }

    @Test
    @Transactional
    public void getAllTripsByEtdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where etd is greater than or equal to DEFAULT_ETD
        defaultTripShouldBeFound("etd.greaterThanOrEqual=" + DEFAULT_ETD);

        // Get all the tripList where etd is greater than or equal to UPDATED_ETD
        defaultTripShouldNotBeFound("etd.greaterThanOrEqual=" + UPDATED_ETD);
    }

    @Test
    @Transactional
    public void getAllTripsByEtdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where etd is less than or equal to DEFAULT_ETD
        defaultTripShouldBeFound("etd.lessThanOrEqual=" + DEFAULT_ETD);

        // Get all the tripList where etd is less than or equal to SMALLER_ETD
        defaultTripShouldNotBeFound("etd.lessThanOrEqual=" + SMALLER_ETD);
    }

    @Test
    @Transactional
    public void getAllTripsByEtdIsLessThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where etd is less than DEFAULT_ETD
        defaultTripShouldNotBeFound("etd.lessThan=" + DEFAULT_ETD);

        // Get all the tripList where etd is less than UPDATED_ETD
        defaultTripShouldBeFound("etd.lessThan=" + UPDATED_ETD);
    }

    @Test
    @Transactional
    public void getAllTripsByEtdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where etd is greater than DEFAULT_ETD
        defaultTripShouldNotBeFound("etd.greaterThan=" + DEFAULT_ETD);

        // Get all the tripList where etd is greater than SMALLER_ETD
        defaultTripShouldBeFound("etd.greaterThan=" + SMALLER_ETD);
    }


    @Test
    @Transactional
    public void getAllTripsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where description equals to DEFAULT_DESCRIPTION
        defaultTripShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the tripList where description equals to UPDATED_DESCRIPTION
        defaultTripShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTripsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where description not equals to DEFAULT_DESCRIPTION
        defaultTripShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the tripList where description not equals to UPDATED_DESCRIPTION
        defaultTripShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTripsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTripShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the tripList where description equals to UPDATED_DESCRIPTION
        defaultTripShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTripsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where description is not null
        defaultTripShouldBeFound("description.specified=true");

        // Get all the tripList where description is null
        defaultTripShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllTripsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where description contains DEFAULT_DESCRIPTION
        defaultTripShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the tripList where description contains UPDATED_DESCRIPTION
        defaultTripShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTripsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where description does not contain DEFAULT_DESCRIPTION
        defaultTripShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the tripList where description does not contain UPDATED_DESCRIPTION
        defaultTripShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllTripsByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where state equals to DEFAULT_STATE
        defaultTripShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the tripList where state equals to UPDATED_STATE
        defaultTripShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllTripsByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where state not equals to DEFAULT_STATE
        defaultTripShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the tripList where state not equals to UPDATED_STATE
        defaultTripShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllTripsByStateIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where state in DEFAULT_STATE or UPDATED_STATE
        defaultTripShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the tripList where state equals to UPDATED_STATE
        defaultTripShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllTripsByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where state is not null
        defaultTripShouldBeFound("state.specified=true");

        // Get all the tripList where state is null
        defaultTripShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    public void getAllTripsByEtaIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where eta equals to DEFAULT_ETA
        defaultTripShouldBeFound("eta.equals=" + DEFAULT_ETA);

        // Get all the tripList where eta equals to UPDATED_ETA
        defaultTripShouldNotBeFound("eta.equals=" + UPDATED_ETA);
    }

    @Test
    @Transactional
    public void getAllTripsByEtaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where eta not equals to DEFAULT_ETA
        defaultTripShouldNotBeFound("eta.notEquals=" + DEFAULT_ETA);

        // Get all the tripList where eta not equals to UPDATED_ETA
        defaultTripShouldBeFound("eta.notEquals=" + UPDATED_ETA);
    }

    @Test
    @Transactional
    public void getAllTripsByEtaIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where eta in DEFAULT_ETA or UPDATED_ETA
        defaultTripShouldBeFound("eta.in=" + DEFAULT_ETA + "," + UPDATED_ETA);

        // Get all the tripList where eta equals to UPDATED_ETA
        defaultTripShouldNotBeFound("eta.in=" + UPDATED_ETA);
    }

    @Test
    @Transactional
    public void getAllTripsByEtaIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where eta is not null
        defaultTripShouldBeFound("eta.specified=true");

        // Get all the tripList where eta is null
        defaultTripShouldNotBeFound("eta.specified=false");
    }

    @Test
    @Transactional
    public void getAllTripsByEtaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where eta is greater than or equal to DEFAULT_ETA
        defaultTripShouldBeFound("eta.greaterThanOrEqual=" + DEFAULT_ETA);

        // Get all the tripList where eta is greater than or equal to UPDATED_ETA
        defaultTripShouldNotBeFound("eta.greaterThanOrEqual=" + UPDATED_ETA);
    }

    @Test
    @Transactional
    public void getAllTripsByEtaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where eta is less than or equal to DEFAULT_ETA
        defaultTripShouldBeFound("eta.lessThanOrEqual=" + DEFAULT_ETA);

        // Get all the tripList where eta is less than or equal to SMALLER_ETA
        defaultTripShouldNotBeFound("eta.lessThanOrEqual=" + SMALLER_ETA);
    }

    @Test
    @Transactional
    public void getAllTripsByEtaIsLessThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where eta is less than DEFAULT_ETA
        defaultTripShouldNotBeFound("eta.lessThan=" + DEFAULT_ETA);

        // Get all the tripList where eta is less than UPDATED_ETA
        defaultTripShouldBeFound("eta.lessThan=" + UPDATED_ETA);
    }

    @Test
    @Transactional
    public void getAllTripsByEtaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where eta is greater than DEFAULT_ETA
        defaultTripShouldNotBeFound("eta.greaterThan=" + DEFAULT_ETA);

        // Get all the tripList where eta is greater than SMALLER_ETA
        defaultTripShouldBeFound("eta.greaterThan=" + SMALLER_ETA);
    }


    @Test
    @Transactional
    public void getAllTripsByDistanceIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where distance equals to DEFAULT_DISTANCE
        defaultTripShouldBeFound("distance.equals=" + DEFAULT_DISTANCE);

        // Get all the tripList where distance equals to UPDATED_DISTANCE
        defaultTripShouldNotBeFound("distance.equals=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTripsByDistanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where distance not equals to DEFAULT_DISTANCE
        defaultTripShouldNotBeFound("distance.notEquals=" + DEFAULT_DISTANCE);

        // Get all the tripList where distance not equals to UPDATED_DISTANCE
        defaultTripShouldBeFound("distance.notEquals=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTripsByDistanceIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where distance in DEFAULT_DISTANCE or UPDATED_DISTANCE
        defaultTripShouldBeFound("distance.in=" + DEFAULT_DISTANCE + "," + UPDATED_DISTANCE);

        // Get all the tripList where distance equals to UPDATED_DISTANCE
        defaultTripShouldNotBeFound("distance.in=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTripsByDistanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where distance is not null
        defaultTripShouldBeFound("distance.specified=true");

        // Get all the tripList where distance is null
        defaultTripShouldNotBeFound("distance.specified=false");
    }

    @Test
    @Transactional
    public void getAllTripsByDistanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where distance is greater than or equal to DEFAULT_DISTANCE
        defaultTripShouldBeFound("distance.greaterThanOrEqual=" + DEFAULT_DISTANCE);

        // Get all the tripList where distance is greater than or equal to UPDATED_DISTANCE
        defaultTripShouldNotBeFound("distance.greaterThanOrEqual=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTripsByDistanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where distance is less than or equal to DEFAULT_DISTANCE
        defaultTripShouldBeFound("distance.lessThanOrEqual=" + DEFAULT_DISTANCE);

        // Get all the tripList where distance is less than or equal to SMALLER_DISTANCE
        defaultTripShouldNotBeFound("distance.lessThanOrEqual=" + SMALLER_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTripsByDistanceIsLessThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where distance is less than DEFAULT_DISTANCE
        defaultTripShouldNotBeFound("distance.lessThan=" + DEFAULT_DISTANCE);

        // Get all the tripList where distance is less than UPDATED_DISTANCE
        defaultTripShouldBeFound("distance.lessThan=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTripsByDistanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where distance is greater than DEFAULT_DISTANCE
        defaultTripShouldNotBeFound("distance.greaterThan=" + DEFAULT_DISTANCE);

        // Get all the tripList where distance is greater than SMALLER_DISTANCE
        defaultTripShouldBeFound("distance.greaterThan=" + SMALLER_DISTANCE);
    }


    @Test
    @Transactional
    public void getAllTripsByOriginIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);
        Origin origin = OriginResourceIT.createEntity(em);
        em.persist(origin);
        em.flush();
        trip.setOrigin(origin);
        tripRepository.saveAndFlush(trip);
        Long originId = origin.getId();

        // Get all the tripList where origin equals to originId
        defaultTripShouldBeFound("originId.equals=" + originId);

        // Get all the tripList where origin equals to originId + 1
        defaultTripShouldNotBeFound("originId.equals=" + (originId + 1));
    }


    @Test
    @Transactional
    public void getAllTripsByDestinationIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);
        Destination destination = DestinationResourceIT.createEntity(em);
        em.persist(destination);
        em.flush();
        trip.setDestination(destination);
        tripRepository.saveAndFlush(trip);
        Long destinationId = destination.getId();

        // Get all the tripList where destination equals to destinationId
        defaultTripShouldBeFound("destinationId.equals=" + destinationId);

        // Get all the tripList where destination equals to destinationId + 1
        defaultTripShouldNotBeFound("destinationId.equals=" + (destinationId + 1));
    }


    @Test
    @Transactional
    public void getAllTripsByConversationIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);
        Conversation conversation = ConversationResourceIT.createEntity(em);
        em.persist(conversation);
        em.flush();
        trip.addConversation(conversation);
        tripRepository.saveAndFlush(trip);
        Long conversationId = conversation.getId();

        // Get all the tripList where conversation equals to conversationId
        defaultTripShouldBeFound("conversationId.equals=" + conversationId);

        // Get all the tripList where conversation equals to conversationId + 1
        defaultTripShouldNotBeFound("conversationId.equals=" + (conversationId + 1));
    }


    @Test
    @Transactional
    public void getAllTripsByTruckIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);
        Truck truck = TruckResourceIT.createEntity(em);
        em.persist(truck);
        em.flush();
        trip.setTruck(truck);
        truck.setTrip(trip);
        tripRepository.saveAndFlush(trip);
        Long truckId = truck.getId();

        // Get all the tripList where truck equals to truckId
        defaultTripShouldBeFound("truckId.equals=" + truckId);

        // Get all the tripList where truck equals to truckId + 1
        defaultTripShouldNotBeFound("truckId.equals=" + (truckId + 1));
    }


    @Test
    @Transactional
    public void getAllTripsByClientAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);
        ClientAccount clientAccount = ClientAccountResourceIT.createEntity(em);
        em.persist(clientAccount);
        em.flush();
        trip.setClientAccount(clientAccount);
        tripRepository.saveAndFlush(trip);
        Long clientAccountId = clientAccount.getId();

        // Get all the tripList where clientAccount equals to clientAccountId
        defaultTripShouldBeFound("clientAccountId.equals=" + clientAccountId);

        // Get all the tripList where clientAccount equals to clientAccountId + 1
        defaultTripShouldNotBeFound("clientAccountId.equals=" + (clientAccountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTripShouldBeFound(String filter) throws Exception {
        restTripMockMvc.perform(get("/api/trips?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trip.getId().intValue())))
            .andExpect(jsonPath("$.[*].isFull").value(hasItem(DEFAULT_IS_FULL.booleanValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].marchandise").value(hasItem(DEFAULT_MARCHANDISE.toString())))
            .andExpect(jsonPath("$.[*].etd").value(hasItem(sameInstant(DEFAULT_ETD))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].eta").value(hasItem(sameInstant(DEFAULT_ETA))))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE)));

        // Check, that the count call also returns 1
        restTripMockMvc.perform(get("/api/trips/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTripShouldNotBeFound(String filter) throws Exception {
        restTripMockMvc.perform(get("/api/trips?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTripMockMvc.perform(get("/api/trips/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTrip() throws Exception {
        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Update the trip
        Trip updatedTrip = tripRepository.findById(trip.getId()).get();
        // Disconnect from session so that the updates on updatedTrip are not directly saved in db
        em.detach(updatedTrip);
        updatedTrip
            .isFull(UPDATED_IS_FULL)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .length(UPDATED_LENGTH)
            .weight(UPDATED_WEIGHT)
            .marchandise(UPDATED_MARCHANDISE)
            .etd(UPDATED_ETD)
            .description(UPDATED_DESCRIPTION)
            .state(UPDATED_STATE)
            .eta(UPDATED_ETA)
            .distance(UPDATED_DISTANCE);
        TripDTO tripDTO = tripMapper.toDto(updatedTrip);

        restTripMockMvc.perform(put("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isOk());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.isIsFull()).isEqualTo(UPDATED_IS_FULL);
        assertThat(testTrip.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testTrip.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testTrip.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testTrip.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testTrip.getMarchandise()).isEqualTo(UPDATED_MARCHANDISE);
        assertThat(testTrip.getEtd()).isEqualTo(UPDATED_ETD);
        assertThat(testTrip.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTrip.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testTrip.getEta()).isEqualTo(UPDATED_ETA);
        assertThat(testTrip.getDistance()).isEqualTo(UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingTrip() throws Exception {
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Create the Trip
        TripDTO tripDTO = tripMapper.toDto(trip);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTripMockMvc.perform(put("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        int databaseSizeBeforeDelete = tripRepository.findAll().size();

        // Delete the trip
        restTripMockMvc.perform(delete("/api/trips/{id}", trip.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
