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

import com.abrid.dropme.domain.Reputation;
import com.abrid.dropme.domain.*; // for static metamodels
import com.abrid.dropme.repository.ReputationRepository;
import com.abrid.dropme.service.dto.ReputationCriteria;
import com.abrid.dropme.service.dto.ReputationDTO;
import com.abrid.dropme.service.mapper.ReputationMapper;

/**
 * Service for executing complex queries for {@link Reputation} entities in the database.
 * The main input is a {@link ReputationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReputationDTO} or a {@link Page} of {@link ReputationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReputationQueryService extends QueryService<Reputation> {

    private final Logger log = LoggerFactory.getLogger(ReputationQueryService.class);

    private final ReputationRepository reputationRepository;

    private final ReputationMapper reputationMapper;

    public ReputationQueryService(ReputationRepository reputationRepository, ReputationMapper reputationMapper) {
        this.reputationRepository = reputationRepository;
        this.reputationMapper = reputationMapper;
    }

    /**
     * Return a {@link List} of {@link ReputationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReputationDTO> findByCriteria(ReputationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Reputation> specification = createSpecification(criteria);
        return reputationMapper.toDto(reputationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReputationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReputationDTO> findByCriteria(ReputationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Reputation> specification = createSpecification(criteria);
        return reputationRepository.findAll(specification, page)
            .map(reputationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReputationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Reputation> specification = createSpecification(criteria);
        return reputationRepository.count(specification);
    }

    /**
     * Function to convert {@link ReputationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Reputation> createSpecification(ReputationCriteria criteria) {
        Specification<Reputation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Reputation_.id));
            }
            if (criteria.getRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRate(), Reputation_.rate));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), Reputation_.comment));
            }
            if (criteria.getTransporterAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getTransporterAccountId(),
                    root -> root.join(Reputation_.transporterAccount, JoinType.LEFT).get(TransporterAccount_.id)));
            }
            if (criteria.getClientAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getClientAccountId(),
                    root -> root.join(Reputation_.clientAccount, JoinType.LEFT).get(ClientAccount_.id)));
            }
        }
        return specification;
    }
}
