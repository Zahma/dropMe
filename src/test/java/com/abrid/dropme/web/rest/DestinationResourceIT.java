package com.abrid.dropme.web.rest;

import com.abrid.dropme.DropMeApp;
import com.abrid.dropme.domain.Destination;
import com.abrid.dropme.domain.Location;
import com.abrid.dropme.domain.Trip;
import com.abrid.dropme.repository.DestinationRepository;
import com.abrid.dropme.service.DestinationService;
import com.abrid.dropme.service.dto.DestinationDTO;
import com.abrid.dropme.service.mapper.DestinationMapper;
import com.abrid.dropme.web.rest.errors.ExceptionTranslator;
import com.abrid.dropme.service.dto.DestinationCriteria;
import com.abrid.dropme.service.DestinationQueryService;

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
 * Integration tests for the {@link DestinationResource} REST controller.
 */
@SpringBootTest(classes = DropMeApp.class)
public class DestinationResourceIT {

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private DestinationMapper destinationMapper;

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private DestinationQueryService destinationQueryService;

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

    private MockMvc restDestinationMockMvc;

    private Destination destination;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DestinationResource destinationResource = new DestinationResource(destinationService, destinationQueryService);
        this.restDestinationMockMvc = MockMvcBuilders.standaloneSetup(destinationResource)
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
    public static Destination createEntity(EntityManager em) {
        Destination destination = new Destination();
        return destination;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Destination createUpdatedEntity(EntityManager em) {
        Destination destination = new Destination();
        return destination;
    }

    @BeforeEach
    public void initTest() {
        destination = createEntity(em);
    }

    @Test
    @Transactional
    public void createDestination() throws Exception {
        int databaseSizeBeforeCreate = destinationRepository.findAll().size();

        // Create the Destination
        DestinationDTO destinationDTO = destinationMapper.toDto(destination);
        restDestinationMockMvc.perform(post("/api/destinations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(destinationDTO)))
            .andExpect(status().isCreated());

        // Validate the Destination in the database
        List<Destination> destinationList = destinationRepository.findAll();
        assertThat(destinationList).hasSize(databaseSizeBeforeCreate + 1);
        Destination testDestination = destinationList.get(destinationList.size() - 1);
    }

    @Test
    @Transactional
    public void createDestinationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = destinationRepository.findAll().size();

        // Create the Destination with an existing ID
        destination.setId(1L);
        DestinationDTO destinationDTO = destinationMapper.toDto(destination);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDestinationMockMvc.perform(post("/api/destinations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(destinationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Destination in the database
        List<Destination> destinationList = destinationRepository.findAll();
        assertThat(destinationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDestinations() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);

        // Get all the destinationList
        restDestinationMockMvc.perform(get("/api/destinations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(destination.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDestination() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);

        // Get the destination
        restDestinationMockMvc.perform(get("/api/destinations/{id}", destination.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(destination.getId().intValue()));
    }


    @Test
    @Transactional
    public void getDestinationsByIdFiltering() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);

        Long id = destination.getId();

        defaultDestinationShouldBeFound("id.equals=" + id);
        defaultDestinationShouldNotBeFound("id.notEquals=" + id);

        defaultDestinationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDestinationShouldNotBeFound("id.greaterThan=" + id);

        defaultDestinationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDestinationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDestinationsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);
        Location location = LocationResourceIT.createEntity(em);
        em.persist(location);
        em.flush();
        destination.setLocation(location);
        location.setDestination(destination);
        destinationRepository.saveAndFlush(destination);
        Long locationId = location.getId();

        // Get all the destinationList where location equals to locationId
        defaultDestinationShouldBeFound("locationId.equals=" + locationId);

        // Get all the destinationList where location equals to locationId + 1
        defaultDestinationShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }


    @Test
    @Transactional
    public void getAllDestinationsByTripIsEqualToSomething() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);
        Trip trip = TripResourceIT.createEntity(em);
        em.persist(trip);
        em.flush();
        destination.setTrip(trip);
        trip.setDestination(destination);
        destinationRepository.saveAndFlush(destination);
        Long tripId = trip.getId();

        // Get all the destinationList where trip equals to tripId
        defaultDestinationShouldBeFound("tripId.equals=" + tripId);

        // Get all the destinationList where trip equals to tripId + 1
        defaultDestinationShouldNotBeFound("tripId.equals=" + (tripId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDestinationShouldBeFound(String filter) throws Exception {
        restDestinationMockMvc.perform(get("/api/destinations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(destination.getId().intValue())));

        // Check, that the count call also returns 1
        restDestinationMockMvc.perform(get("/api/destinations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDestinationShouldNotBeFound(String filter) throws Exception {
        restDestinationMockMvc.perform(get("/api/destinations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDestinationMockMvc.perform(get("/api/destinations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDestination() throws Exception {
        // Get the destination
        restDestinationMockMvc.perform(get("/api/destinations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDestination() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);

        int databaseSizeBeforeUpdate = destinationRepository.findAll().size();

        // Update the destination
        Destination updatedDestination = destinationRepository.findById(destination.getId()).get();
        // Disconnect from session so that the updates on updatedDestination are not directly saved in db
        em.detach(updatedDestination);
        DestinationDTO destinationDTO = destinationMapper.toDto(updatedDestination);

        restDestinationMockMvc.perform(put("/api/destinations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(destinationDTO)))
            .andExpect(status().isOk());

        // Validate the Destination in the database
        List<Destination> destinationList = destinationRepository.findAll();
        assertThat(destinationList).hasSize(databaseSizeBeforeUpdate);
        Destination testDestination = destinationList.get(destinationList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingDestination() throws Exception {
        int databaseSizeBeforeUpdate = destinationRepository.findAll().size();

        // Create the Destination
        DestinationDTO destinationDTO = destinationMapper.toDto(destination);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDestinationMockMvc.perform(put("/api/destinations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(destinationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Destination in the database
        List<Destination> destinationList = destinationRepository.findAll();
        assertThat(destinationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDestination() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);

        int databaseSizeBeforeDelete = destinationRepository.findAll().size();

        // Delete the destination
        restDestinationMockMvc.perform(delete("/api/destinations/{id}", destination.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Destination> destinationList = destinationRepository.findAll();
        assertThat(destinationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
