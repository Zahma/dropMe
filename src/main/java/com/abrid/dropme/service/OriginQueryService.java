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

import com.abrid.dropme.domain.Origin;
import com.abrid.dropme.domain.*; // for static metamodels
import com.abrid.dropme.repository.OriginRepository;
import com.abrid.dropme.service.dto.OriginCriteria;
import com.abrid.dropme.service.dto.OriginDTO;
import com.abrid.dropme.service.mapper.OriginMapper;

/**
 * Service for executing complex queries for {@link Origin} entities in the database.
 * The main input is a {@link OriginCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OriginDTO} or a {@link Page} of {@link OriginDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OriginQueryService extends QueryService<Origin> {

    private final Logger log = LoggerFactory.getLogger(OriginQueryService.class);

    private final OriginRepository originRepository;

    private final OriginMapper originMapper;

    public OriginQueryService(OriginRepository originRepository, OriginMapper originMapper) {
        this.originRepository = originRepository;
        this.originMapper = originMapper;
    }

    /**
     * Return a {@link List} of {@link OriginDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OriginDTO> findByCriteria(OriginCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Origin> specification = createSpecification(criteria);
        return originMapper.toDto(originRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OriginDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OriginDTO> findByCriteria(OriginCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Origin> specification = createSpecification(criteria);
        return originRepository.findAll(specification, page)
            .map(originMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OriginCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Origin> specification = createSpecification(criteria);
        return originRepository.count(specification);
    }

    /**
     * Function to convert {@link OriginCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Origin> createSpecification(OriginCriteria criteria) {
        Specification<Origin> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Origin_.id));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationId(),
                    root -> root.join(Origin_.location, JoinType.LEFT).get(Location_.id)));
            }
            if (criteria.getTripId() != null) {
                specification = specification.and(buildSpecification(criteria.getTripId(),
                    root -> root.join(Origin_.trip, JoinType.LEFT).get(Trip_.id)));
            }
        }
        return specification;
    }
}
