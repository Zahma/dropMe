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

import com.abrid.dropme.domain.Trip;
import com.abrid.dropme.domain.*; // for static metamodels
import com.abrid.dropme.repository.TripRepository;
import com.abrid.dropme.service.dto.TripCriteria;
import com.abrid.dropme.service.dto.TripDTO;
import com.abrid.dropme.service.mapper.TripMapper;

/**
 * Service for executing complex queries for {@link Trip} entities in the database.
 * The main input is a {@link TripCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TripDTO} or a {@link Page} of {@link TripDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TripQueryService extends QueryService<Trip> {

    private final Logger log = LoggerFactory.getLogger(TripQueryService.class);

    private final TripRepository tripRepository;

    private final TripMapper tripMapper;

    public TripQueryService(TripRepository tripRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
    }

    /**
     * Return a {@link List} of {@link TripDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TripDTO> findByCriteria(TripCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Trip> specification = createSpecification(criteria);
        return tripMapper.toDto(tripRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TripDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TripDTO> findByCriteria(TripCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Trip> specification = createSpecification(criteria);
        return tripRepository.findAll(specification, page)
            .map(tripMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TripCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Trip> specification = createSpecification(criteria);
        return tripRepository.count(specification);
    }

    /**
     * Function to convert {@link TripCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Trip> createSpecification(TripCriteria criteria) {
        Specification<Trip> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Trip_.id));
            }
            if (criteria.getIsFull() != null) {
                specification = specification.and(buildSpecification(criteria.getIsFull(), Trip_.isFull));
            }
            if (criteria.getWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWidth(), Trip_.width));
            }
            if (criteria.getHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeight(), Trip_.height));
            }
            if (criteria.getLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLength(), Trip_.length));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), Trip_.weight));
            }
            if (criteria.getMarchandise() != null) {
                specification = specification.and(buildSpecification(criteria.getMarchandise(), Trip_.marchandise));
            }
            if (criteria.getEtd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEtd(), Trip_.etd));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Trip_.description));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildSpecification(criteria.getState(), Trip_.state));
            }
            if (criteria.getEta() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEta(), Trip_.eta));
            }
            if (criteria.getDistance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDistance(), Trip_.distance));
            }
            if (criteria.getOriginId() != null) {
                specification = specification.and(buildSpecification(criteria.getOriginId(),
                    root -> root.join(Trip_.origin, JoinType.LEFT).get(Origin_.id)));
            }
            if (criteria.getDestinationId() != null) {
                specification = specification.and(buildSpecification(criteria.getDestinationId(),
                    root -> root.join(Trip_.destination, JoinType.LEFT).get(Destination_.id)));
            }
            if (criteria.getConversationId() != null) {
                specification = specification.and(buildSpecification(criteria.getConversationId(),
                    root -> root.join(Trip_.conversations, JoinType.LEFT).get(Conversation_.id)));
            }
            if (criteria.getTruckId() != null) {
                specification = specification.and(buildSpecification(criteria.getTruckId(),
                    root -> root.join(Trip_.truck, JoinType.LEFT).get(Truck_.id)));
            }
            if (criteria.getClientAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getClientAccountId(),
                    root -> root.join(Trip_.clientAccount, JoinType.LEFT).get(ClientAccount_.id)));
            }
        }
        return specification;
    }
}
