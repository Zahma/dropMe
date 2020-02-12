package com.abrid.dropme.web.rest;

import com.abrid.dropme.DropMeApp;
import com.abrid.dropme.domain.Conversation;
import com.abrid.dropme.domain.Chat;
import com.abrid.dropme.domain.Trip;
import com.abrid.dropme.domain.Truck;
import com.abrid.dropme.repository.ConversationRepository;
import com.abrid.dropme.service.ConversationService;
import com.abrid.dropme.service.dto.ConversationDTO;
import com.abrid.dropme.service.mapper.ConversationMapper;
import com.abrid.dropme.web.rest.errors.ExceptionTranslator;
import com.abrid.dropme.service.dto.ConversationCriteria;
import com.abrid.dropme.service.ConversationQueryService;

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
 * Integration tests for the {@link ConversationResource} REST controller.
 */
@SpringBootTest(classes = DropMeApp.class)
public class ConversationResourceIT {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private ConversationMapper conversationMapper;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private ConversationQueryService conversationQueryService;

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

    private MockMvc restConversationMockMvc;

    private Conversation conversation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConversationResource conversationResource = new ConversationResource(conversationService, conversationQueryService);
        this.restConversationMockMvc = MockMvcBuilders.standaloneSetup(conversationResource)
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
    public static Conversation createEntity(EntityManager em) {
        Conversation conversation = new Conversation();
        return conversation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conversation createUpdatedEntity(EntityManager em) {
        Conversation conversation = new Conversation();
        return conversation;
    }

    @BeforeEach
    public void initTest() {
        conversation = createEntity(em);
    }

    @Test
    @Transactional
    public void createConversation() throws Exception {
        int databaseSizeBeforeCreate = conversationRepository.findAll().size();

        // Create the Conversation
        ConversationDTO conversationDTO = conversationMapper.toDto(conversation);
        restConversationMockMvc.perform(post("/api/conversations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conversationDTO)))
            .andExpect(status().isCreated());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeCreate + 1);
        Conversation testConversation = conversationList.get(conversationList.size() - 1);
    }

    @Test
    @Transactional
    public void createConversationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conversationRepository.findAll().size();

        // Create the Conversation with an existing ID
        conversation.setId(1L);
        ConversationDTO conversationDTO = conversationMapper.toDto(conversation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConversationMockMvc.perform(post("/api/conversations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conversationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConversations() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        // Get all the conversationList
        restConversationMockMvc.perform(get("/api/conversations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conversation.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getConversation() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        // Get the conversation
        restConversationMockMvc.perform(get("/api/conversations/{id}", conversation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conversation.getId().intValue()));
    }


    @Test
    @Transactional
    public void getConversationsByIdFiltering() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        Long id = conversation.getId();

        defaultConversationShouldBeFound("id.equals=" + id);
        defaultConversationShouldNotBeFound("id.notEquals=" + id);

        defaultConversationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConversationShouldNotBeFound("id.greaterThan=" + id);

        defaultConversationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConversationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllConversationsByChatIsEqualToSomething() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);
        Chat chat = ChatResourceIT.createEntity(em);
        em.persist(chat);
        em.flush();
        conversation.addChat(chat);
        conversationRepository.saveAndFlush(conversation);
        Long chatId = chat.getId();

        // Get all the conversationList where chat equals to chatId
        defaultConversationShouldBeFound("chatId.equals=" + chatId);

        // Get all the conversationList where chat equals to chatId + 1
        defaultConversationShouldNotBeFound("chatId.equals=" + (chatId + 1));
    }


    @Test
    @Transactional
    public void getAllConversationsByTripIsEqualToSomething() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);
        Trip trip = TripResourceIT.createEntity(em);
        em.persist(trip);
        em.flush();
        conversation.setTrip(trip);
        conversationRepository.saveAndFlush(conversation);
        Long tripId = trip.getId();

        // Get all the conversationList where trip equals to tripId
        defaultConversationShouldBeFound("tripId.equals=" + tripId);

        // Get all the conversationList where trip equals to tripId + 1
        defaultConversationShouldNotBeFound("tripId.equals=" + (tripId + 1));
    }


    @Test
    @Transactional
    public void getAllConversationsByTruckIsEqualToSomething() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);
        Truck truck = TruckResourceIT.createEntity(em);
        em.persist(truck);
        em.flush();
        conversation.setTruck(truck);
        conversationRepository.saveAndFlush(conversation);
        Long truckId = truck.getId();

        // Get all the conversationList where truck equals to truckId
        defaultConversationShouldBeFound("truckId.equals=" + truckId);

        // Get all the conversationList where truck equals to truckId + 1
        defaultConversationShouldNotBeFound("truckId.equals=" + (truckId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConversationShouldBeFound(String filter) throws Exception {
        restConversationMockMvc.perform(get("/api/conversations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conversation.getId().intValue())));

        // Check, that the count call also returns 1
        restConversationMockMvc.perform(get("/api/conversations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConversationShouldNotBeFound(String filter) throws Exception {
        restConversationMockMvc.perform(get("/api/conversations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConversationMockMvc.perform(get("/api/conversations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingConversation() throws Exception {
        // Get the conversation
        restConversationMockMvc.perform(get("/api/conversations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConversation() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();

        // Update the conversation
        Conversation updatedConversation = conversationRepository.findById(conversation.getId()).get();
        // Disconnect from session so that the updates on updatedConversation are not directly saved in db
        em.detach(updatedConversation);
        ConversationDTO conversationDTO = conversationMapper.toDto(updatedConversation);

        restConversationMockMvc.perform(put("/api/conversations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conversationDTO)))
            .andExpect(status().isOk());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
        Conversation testConversation = conversationList.get(conversationList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingConversation() throws Exception {
        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();

        // Create the Conversation
        ConversationDTO conversationDTO = conversationMapper.toDto(conversation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConversationMockMvc.perform(put("/api/conversations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conversationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConversation() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        int databaseSizeBeforeDelete = conversationRepository.findAll().size();

        // Delete the conversation
        restConversationMockMvc.perform(delete("/api/conversations/{id}", conversation.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
