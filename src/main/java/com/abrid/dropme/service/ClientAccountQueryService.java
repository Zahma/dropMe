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

import com.abrid.dropme.domain.ClientAccount;
import com.abrid.dropme.domain.*; // for static metamodels
import com.abrid.dropme.repository.ClientAccountRepository;
import com.abrid.dropme.service.dto.ClientAccountCriteria;
import com.abrid.dropme.service.dto.ClientAccountDTO;
import com.abrid.dropme.service.mapper.ClientAccountMapper;

/**
 * Service for executing complex queries for {@link ClientAccount} entities in the database.
 * The main input is a {@link ClientAccountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClientAccountDTO} or a {@link Page} of {@link ClientAccountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClientAccountQueryService extends QueryService<ClientAccount> {

    private final Logger log = LoggerFactory.getLogger(ClientAccountQueryService.class);

    private final ClientAccountRepository clientAccountRepository;

    private final ClientAccountMapper clientAccountMapper;

    public ClientAccountQueryService(ClientAccountRepository clientAccountRepository, ClientAccountMapper clientAccountMapper) {
        this.clientAccountRepository = clientAccountRepository;
        this.clientAccountMapper = clientAccountMapper;
    }

    /**
     * Return a {@link List} of {@link ClientAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClientAccountDTO> findByCriteria(ClientAccountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClientAccount> specification = createSpecification(criteria);
        return clientAccountMapper.toDto(clientAccountRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClientAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientAccountDTO> findByCriteria(ClientAccountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClientAccount> specification = createSpecification(criteria);
        return clientAccountRepository.findAll(specification, page)
            .map(clientAccountMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClientAccountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClientAccount> specification = createSpecification(criteria);
        return clientAccountRepository.count(specification);
    }

    /**
     * Function to convert {@link ClientAccountCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClientAccount> createSpecification(ClientAccountCriteria criteria) {
        Specification<ClientAccount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClientAccount_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), ClientAccount_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), ClientAccount_.lastName));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), ClientAccount_.phone));
            }
            if (criteria.getReferredBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferredBy(), ClientAccount_.referredBy));
            }
            if (criteria.getReferal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferal(), ClientAccount_.referal));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ClientAccount_.activated));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(ClientAccount_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getReputationId() != null) {
                specification = specification.and(buildSpecification(criteria.getReputationId(),
                    root -> root.join(ClientAccount_.reputations, JoinType.LEFT).get(Reputation_.id)));
            }
            if (criteria.getTripId() != null) {
                specification = specification.and(buildSpecification(criteria.getTripId(),
                    root -> root.join(ClientAccount_.trips, JoinType.LEFT).get(Trip_.id)));
            }
        }
        return specification;
    }
}
