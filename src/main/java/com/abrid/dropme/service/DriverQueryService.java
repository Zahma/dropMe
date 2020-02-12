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

import com.abrid.dropme.domain.Driver;
import com.abrid.dropme.domain.*; // for static metamodels
import com.abrid.dropme.repository.DriverRepository;
import com.abrid.dropme.service.dto.DriverCriteria;
import com.abrid.dropme.service.dto.DriverDTO;
import com.abrid.dropme.service.mapper.DriverMapper;

/**
 * Service for executing complex queries for {@link Driver} entities in the database.
 * The main input is a {@link DriverCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DriverDTO} or a {@link Page} of {@link DriverDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DriverQueryService extends QueryService<Driver> {

    private final Logger log = LoggerFactory.getLogger(DriverQueryService.class);

    private final DriverRepository driverRepository;

    private final DriverMapper driverMapper;

    public DriverQueryService(DriverRepository driverRepository, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
    }

    /**
     * Return a {@link List} of {@link DriverDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DriverDTO> findByCriteria(DriverCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Driver> specification = createSpecification(criteria);
        return driverMapper.toDto(driverRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DriverDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DriverDTO> findByCriteria(DriverCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Driver> specification = createSpecification(criteria);
        return driverRepository.findAll(specification, page)
            .map(driverMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DriverCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Driver> specification = createSpecification(criteria);
        return driverRepository.count(specification);
    }

    /**
     * Function to convert {@link DriverCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Driver> createSpecification(DriverCriteria criteria) {
        Specification<Driver> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Driver_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Driver_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Driver_.lastName));
            }
            if (criteria.getCurrentCoordinate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentCoordinate(), Driver_.currentCoordinate));
            }
            if (criteria.getTruckId() != null) {
                specification = specification.and(buildSpecification(criteria.getTruckId(),
                    root -> root.join(Driver_.truck, JoinType.LEFT).get(Truck_.id)));
            }
        }
        return specification;
    }
}
