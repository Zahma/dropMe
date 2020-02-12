package com.abrid.dropme.web.rest;

import com.abrid.dropme.DropMeApp;
import com.abrid.dropme.domain.Origin;
import com.abrid.dropme.domain.Location;
import com.abrid.dropme.domain.Trip;
import com.abrid.dropme.repository.OriginRepository;
import com.abrid.dropme.service.OriginService;
import com.abrid.dropme.service.dto.OriginDTO;
import com.abrid.dropme.service.mapper.OriginMapper;
import com.abrid.dropme.web.rest.errors.ExceptionTranslator;
import com.abrid.dropme.service.dto.OriginCriteria;
import com.abrid.dropme.service.OriginQueryService;

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
 * Integration tests for the {@link OriginResource} REST controller.
 */
@SpringBootTest(classes = DropMeApp.class)
public class OriginResourceIT {

    @Autowired
    private OriginRepository originRepository;

    @Autowired
    private OriginMapper originMapper;

    @Autowired
    private OriginService originService;

    @Autowired
    private OriginQueryService originQueryService;

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

    private MockMvc restOriginMockMvc;

    private Origin origin;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OriginResource originResource = new OriginResource(originService, originQueryService);
        this.restOriginMockMvc = MockMvcBuilders.standaloneSetup(originResource)
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
    public static Origin createEntity(EntityManager em) {
        Origin origin = new Origin();
        return origin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Origin createUpdatedEntity(EntityManager em) {
        Origin origin = new Origin();
        return origin;
    }

    @BeforeEach
    public void initTest() {
        origin = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrigin() throws Exception {
        int databaseSizeBeforeCreate = originRepository.findAll().size();

        // Create the Origin
        OriginDTO originDTO = originMapper.toDto(origin);
        restOriginMockMvc.perform(post("/api/origins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(originDTO)))
            .andExpect(status().isCreated());

        // Validate the Origin in the database
        List<Origin> originList = originRepository.findAll();
        assertThat(originList).hasSize(databaseSizeBeforeCreate + 1);
        Origin testOrigin = originList.get(originList.size() - 1);
    }

    @Test
    @Transactional
    public void createOriginWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = originRepository.findAll().size();

        // Create the Origin with an existing ID
        origin.setId(1L);
        OriginDTO originDTO = originMapper.toDto(origin);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOriginMockMvc.perform(post("/api/origins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(originDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Origin in the database
        List<Origin> originList = originRepository.findAll();
        assertThat(originList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrigins() throws Exception {
        // Initialize the database
        originRepository.saveAndFlush(origin);

        // Get all the originList
        restOriginMockMvc.perform(get("/api/origins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(origin.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getOrigin() throws Exception {
        // Initialize the database
        originRepository.saveAndFlush(origin);

        // Get the origin
        restOriginMockMvc.perform(get("/api/origins/{id}", origin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(origin.getId().intValue()));
    }


    @Test
    @Transactional
    public void getOriginsByIdFiltering() throws Exception {
        // Initialize the database
        originRepository.saveAndFlush(origin);

        Long id = origin.getId();

        defaultOriginShouldBeFound("id.equals=" + id);
        defaultOriginShouldNotBeFound("id.notEquals=" + id);

        defaultOriginShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOriginShouldNotBeFound("id.greaterThan=" + id);

        defaultOriginShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOriginShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOriginsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        originRepository.saveAndFlush(origin);
        Location location = LocationResourceIT.createEntity(em);
        em.persist(location);
        em.flush();
        origin.setLocation(location);
        location.setOrigin(origin);
        originRepository.saveAndFlush(origin);
        Long locationId = location.getId();

        // Get all the originList where location equals to locationId
        defaultOriginShouldBeFound("locationId.equals=" + locationId);

        // Get all the originList where location equals to locationId + 1
        defaultOriginShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }


    @Test
    @Transactional
    public void getAllOriginsByTripIsEqualToSomething() throws Exception {
        // Initialize the database
        originRepository.saveAndFlush(origin);
        Trip trip = TripResourceIT.createEntity(em);
        em.persist(trip);
        em.flush();
        origin.setTrip(trip);
        trip.setOrigin(origin);
        originRepository.saveAndFlush(origin);
        Long tripId = trip.getId();

        // Get all the originList where trip equals to tripId
        defaultOriginShouldBeFound("tripId.equals=" + tripId);

        // Get all the originList where trip equals to tripId + 1
        defaultOriginShouldNotBeFound("tripId.equals=" + (tripId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOriginShouldBeFound(String filter) throws Exception {
        restOriginMockMvc.perform(get("/api/origins?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(origin.getId().intValue())));

        // Check, that the count call also returns 1
        restOriginMockMvc.perform(get("/api/origins/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOriginShouldNotBeFound(String filter) throws Exception {
        restOriginMockMvc.perform(get("/api/origins?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOriginMockMvc.perform(get("/api/origins/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOrigin() throws Exception {
        // Get the origin
        restOriginMockMvc.perform(get("/api/origins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrigin() throws Exception {
        // Initialize the database
        originRepository.saveAndFlush(origin);

        int databaseSizeBeforeUpdate = originRepository.findAll().size();

        // Update the origin
        Origin updatedOrigin = originRepository.findById(origin.getId()).get();
        // Disconnect from session so that the updates on updatedOrigin are not directly saved in db
        em.detach(updatedOrigin);
        OriginDTO originDTO = originMapper.toDto(updatedOrigin);

        restOriginMockMvc.perform(put("/api/origins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(originDTO)))
            .andExpect(status().isOk());

        // Validate the Origin in the database
        List<Origin> originList = originRepository.findAll();
        assertThat(originList).hasSize(databaseSizeBeforeUpdate);
        Origin testOrigin = originList.get(originList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingOrigin() throws Exception {
        int databaseSizeBeforeUpdate = originRepository.findAll().size();

        // Create the Origin
        OriginDTO originDTO = originMapper.toDto(origin);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOriginMockMvc.perform(put("/api/origins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(originDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Origin in the database
        List<Origin> originList = originRepository.findAll();
        assertThat(originList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrigin() throws Exception {
        // Initialize the database
        originRepository.saveAndFlush(origin);

        int databaseSizeBeforeDelete = originRepository.findAll().size();

        // Delete the origin
        restOriginMockMvc.perform(delete("/api/origins/{id}", origin.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Origin> originList = originRepository.findAll();
        assertThat(originList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
