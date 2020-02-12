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

import com.abrid.dropme.domain.TransporterAccount;
import com.abrid.dropme.domain.*; // for static metamodels
import com.abrid.dropme.repository.TransporterAccountRepository;
import com.abrid.dropme.service.dto.TransporterAccountCriteria;
import com.abrid.dropme.service.dto.TransporterAccountDTO;
import com.abrid.dropme.service.mapper.TransporterAccountMapper;

/**
 * Service for executing complex queries for {@link TransporterAccount} entities in the database.
 * The main input is a {@link TransporterAccountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransporterAccountDTO} or a {@link Page} of {@link TransporterAccountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransporterAccountQueryService extends QueryService<TransporterAccount> {

    private final Logger log = LoggerFactory.getLogger(TransporterAccountQueryService.class);

    private final TransporterAccountRepository transporterAccountRepository;

    private final TransporterAccountMapper transporterAccountMapper;

    public TransporterAccountQueryService(TransporterAccountRepository transporterAccountRepository, TransporterAccountMapper transporterAccountMapper) {
        this.transporterAccountRepository = transporterAccountRepository;
        this.transporterAccountMapper = transporterAccountMapper;
    }

    /**
     * Return a {@link List} of {@link TransporterAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransporterAccountDTO> findByCriteria(TransporterAccountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransporterAccount> specification = createSpecification(criteria);
        return transporterAccountMapper.toDto(transporterAccountRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransporterAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransporterAccountDTO> findByCriteria(TransporterAccountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransporterAccount> specification = createSpecification(criteria);
        return transporterAccountRepository.findAll(specification, page)
            .map(transporterAccountMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransporterAccountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransporterAccount> specification = createSpecification(criteria);
        return transporterAccountRepository.count(specification);
    }

    /**
     * Function to convert {@link TransporterAccountCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TransporterAccount> createSpecification(TransporterAccountCriteria criteria) {
        Specification<TransporterAccount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TransporterAccount_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TransporterAccount_.name));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), TransporterAccount_.phone));
            }
            if (criteria.getManagerFName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManagerFName(), TransporterAccount_.managerFName));
            }
            if (criteria.getManagerLName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManagerLName(), TransporterAccount_.managerLName));
            }
            if (criteria.getBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBalance(), TransporterAccount_.balance));
            }
            if (criteria.getReferal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferal(), TransporterAccount_.referal));
            }
            if (criteria.getReferedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferedBy(), TransporterAccount_.referedBy));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), TransporterAccount_.activated));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(TransporterAccount_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getTruckId() != null) {
                specification = specification.and(buildSpecification(criteria.getTruckId(),
                    root -> root.join(TransporterAccount_.trucks, JoinType.LEFT).get(Truck_.id)));
            }
            if (criteria.getReputationId() != null) {
                specification = specification.and(buildSpecification(criteria.getReputationId(),
                    root -> root.join(TransporterAccount_.reputations, JoinType.LEFT).get(Reputation_.id)));
            }
        }
        return specification;
    }
}
