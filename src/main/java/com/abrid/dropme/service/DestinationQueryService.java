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

import com.abrid.dropme.domain.Destination;
import com.abrid.dropme.domain.*; // for static metamodels
import com.abrid.dropme.repository.DestinationRepository;
import com.abrid.dropme.service.dto.DestinationCriteria;
import com.abrid.dropme.service.dto.DestinationDTO;
import com.abrid.dropme.service.mapper.DestinationMapper;

/**
 * Service for executing complex queries for {@link Destination} entities in the database.
 * The main input is a {@link DestinationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DestinationDTO} or a {@link Page} of {@link DestinationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DestinationQueryService extends QueryService<Destination> {

    private final Logger log = LoggerFactory.getLogger(DestinationQueryService.class);

    private final DestinationRepository destinationRepository;

    private final DestinationMapper destinationMapper;

    public DestinationQueryService(DestinationRepository destinationRepository, DestinationMapper destinationMapper) {
        this.destinationRepository = destinationRepository;
        this.destinationMapper = destinationMapper;
    }

    /**
     * Return a {@link List} of {@link DestinationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DestinationDTO> findByCriteria(DestinationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Destination> specification = createSpecification(criteria);
        return destinationMapper.toDto(destinationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DestinationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DestinationDTO> findByCriteria(DestinationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Destination> specification = createSpecification(criteria);
        return destinationRepository.findAll(specification, page)
            .map(destinationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DestinationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Destination> specification = createSpecification(criteria);
        return destinationRepository.count(specification);
    }

    /**
     * Function to convert {@link DestinationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Destination> createSpecification(DestinationCriteria criteria) {
        Specification<Destination> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Destination_.id));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationId(),
                    root -> root.join(Destination_.location, JoinType.LEFT).get(Location_.id)));
            }
            if (criteria.getTripId() != null) {
                specification = specification.and(buildSpecification(criteria.getTripId(),
                    root -> root.join(Destination_.trip, JoinType.LEFT).get(Trip_.id)));
            }
        }
        return specification;
    }
}
