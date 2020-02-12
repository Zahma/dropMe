package com.abrid.dropme.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.abrid.dropme.domain.Chat;
import com.abrid.dropme.domain.*; // for static metamodels
import com.abrid.dropme.repository.ChatRepository;
import com.abrid.dropme.service.dto.ChatCriteria;
import com.abrid.dropme.service.dto.ChatDTO;
import com.abrid.dropme.service.mapper.ChatMapper;

/**
 * Service for executing complex queries for {@link Chat} entities in the database.
 * The main input is a {@link ChatCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChatDTO} or a {@link Page} of {@link ChatDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChatQueryService extends QueryService<Chat> {

    private final Logger log = LoggerFactory.getLogger(ChatQueryService.class);

    private final ChatRepository chatRepository;

    private final ChatMapper chatMapper;

    public ChatQueryService(ChatRepository chatRepository, ChatMapper chatMapper) {
        this.chatRepository = chatRepository;
        this.chatMapper = chatMapper;
    }

    /**
     * Return a {@link List} of {@link ChatDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChatDTO> findByCriteria(ChatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Chat> specification = createSpecification(criteria);
        return chatMapper.toDto(chatRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ChatDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChatDTO> findByCriteria(ChatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Chat> specification = createSpecification(criteria);
        return chatRepository.findAll(specification, page)
            .map(chatMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChatCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Chat> specification = createSpecification(criteria);
        return chatRepository.count(specification);
    }

    /**
     * Function to convert {@link ChatCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Chat> createSpecification(ChatCriteria criteria) {
        Specification<Chat> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Chat_.id));
            }
            if (criteria.getText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getText(), Chat_.text));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Chat_.date));
            }
            if (criteria.getConversationId() != null) {
                specification = specification.and(buildSpecification(criteria.getConversationId(),
                    root -> root.join(Chat_.conversation, JoinType.LEFT).get(Conversation_.id)));
            }
        }
        return specification;
    }
}
