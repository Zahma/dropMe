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

import com.abrid.dropme.domain.Truck;
import com.abrid.dropme.domain.*; // for static metamodels
import com.abrid.dropme.repository.TruckRepository;
import com.abrid.dropme.service.dto.TruckCriteria;
import com.abrid.dropme.service.dto.TruckDTO;
import com.abrid.dropme.service.mapper.TruckMapper;

/**
 * Service for executing complex queries for {@link Truck} entities in the database.
 * The main input is a {@link TruckCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TruckDTO} or a {@link Page} of {@link TruckDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TruckQueryService extends QueryService<Truck> {

    private final Logger log = LoggerFactory.getLogger(TruckQueryService.class);

    private final TruckRepository truckRepository;

    private final TruckMapper truckMapper;

    public TruckQueryService(TruckRepository truckRepository, TruckMapper truckMapper) {
        this.truckRepository = truckRepository;
        this.truckMapper = truckMapper;
    }

    /**
     * Return a {@link List} of {@link TruckDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TruckDTO> findByCriteria(TruckCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Truck> specification = createSpecification(criteria);
        return truckMapper.toDto(truckRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TruckDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TruckDTO> findByCriteria(TruckCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Truck> specification = createSpecification(criteria);
        return truckRepository.findAll(specification, page)
            .map(truckMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TruckCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Truck> specification = createSpecification(criteria);
        return truckRepository.count(specification);
    }

    /**
     * Function to convert {@link TruckCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Truck> createSpecification(TruckCriteria criteria) {
        Specification<Truck> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Truck_.id));
            }
            if (criteria.getPlateNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlateNumber(), Truck_.plateNumber));
            }
            if (criteria.getConteneurPlateNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConteneurPlateNumber(), Truck_.conteneurPlateNumber));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Truck_.type));
            }
            if (criteria.getWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWidth(), Truck_.width));
            }
            if (criteria.getHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeight(), Truck_.height));
            }
            if (criteria.getLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLength(), Truck_.length));
            }
            if (criteria.getMaxWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxWeight(), Truck_.maxWeight));
            }
            if (criteria.getDriverId() != null) {
                specification = specification.and(buildSpecification(criteria.getDriverId(),
                    root -> root.join(Truck_.driver, JoinType.LEFT).get(Driver_.id)));
            }
            if (criteria.getTripId() != null) {
                specification = specification.and(buildSpecification(criteria.getTripId(),
                    root -> root.join(Truck_.trip, JoinType.LEFT).get(Trip_.id)));
            }
            if (criteria.getConversationId() != null) {
                specification = specification.and(buildSpecification(criteria.getConversationId(),
                    root -> root.join(Truck_.conversations, JoinType.LEFT).get(Conversation_.id)));
            }
            if (criteria.getTransporterAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getTransporterAccountId(),
                    root -> root.join(Truck_.transporterAccount, JoinType.LEFT).get(TransporterAccount_.id)));
            }
        }
        return specification;
    }
}
