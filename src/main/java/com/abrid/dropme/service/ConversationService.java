package com.abrid.dropme.service;

import com.abrid.dropme.domain.Conversation;
import com.abrid.dropme.repository.ConversationRepository;
import com.abrid.dropme.service.dto.ConversationDTO;
import com.abrid.dropme.service.mapper.ConversationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Conversation}.
 */
@Service
@Transactional
public class ConversationService {

    private final Logger log = LoggerFactory.getLogger(ConversationService.class);

    private final ConversationRepository conversationRepository;

    private final ConversationMapper conversationMapper;

    public ConversationService(ConversationRepository conversationRepository, ConversationMapper conversationMapper) {
        this.conversationRepository = conversationRepository;
        this.conversationMapper = conversationMapper;
    }

    /**
     * Save a conversation.
     *
     * @param conversationDTO the entity to save.
     * @return the persisted entity.
     */
    public ConversationDTO save(ConversationDTO conversationDTO) {
        log.debug("Request to save Conversation : {}", conversationDTO);
        Conversation conversation = conversationMapper.toEntity(conversationDTO);
        conversation = conversationRepository.save(conversation);
        return conversationMapper.toDto(conversation);
    }

    /**
     * Get all the conversations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConversationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Conversations");
        return conversationRepository.findAll(pageable)
            .map(conversationMapper::toDto);
    }

    /**
     * Get one conversation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConversationDTO> findOne(Long id) {
        log.debug("Request to get Conversation : {}", id);
        return conversationRepository.findById(id)
            .map(conversationMapper::toDto);
    }

    /**
     * Delete the conversation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Conversation : {}", id);
        conversationRepository.deleteById(id);
    }
}
