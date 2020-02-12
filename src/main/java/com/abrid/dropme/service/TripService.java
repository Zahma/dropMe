package com.abrid.dropme.service;

import com.abrid.dropme.domain.Trip;
import com.abrid.dropme.repository.TripRepository;
import com.abrid.dropme.service.dto.TripDTO;
import com.abrid.dropme.service.mapper.TripMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Trip}.
 */
@Service
@Transactional
public class TripService {

    private final Logger log = LoggerFactory.getLogger(TripService.class);

    private final TripRepository tripRepository;

    private final TripMapper tripMapper;

    public TripService(TripRepository tripRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
    }

    /**
     * Save a trip.
     *
     * @param tripDTO the entity to save.
     * @return the persisted entity.
     */
    public TripDTO save(TripDTO tripDTO) {
        log.debug("Request to save Trip : {}", tripDTO);
        Trip trip = tripMapper.toEntity(tripDTO);
        trip = tripRepository.save(trip);
        return tripMapper.toDto(trip);
    }

    /**
     * Get all the trips.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TripDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trips");
        return tripRepository.findAll(pageable)
            .map(tripMapper::toDto);
    }


    /**
     *  Get all the trips where Truck is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<TripDTO> findAllWhereTruckIsNull() {
        log.debug("Request to get all trips where Truck is null");
        return StreamSupport
            .stream(tripRepository.findAll().spliterator(), false)
            .filter(trip -> trip.getTruck() == null)
            .map(tripMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one trip by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TripDTO> findOne(Long id) {
        log.debug("Request to get Trip : {}", id);
        return tripRepository.findById(id)
            .map(tripMapper::toDto);
    }

    /**
     * Delete the trip by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Trip : {}", id);
        tripRepository.deleteById(id);
    }
}
