package com.abrid.dropme.service;

import com.abrid.dropme.domain.Destination;
import com.abrid.dropme.repository.DestinationRepository;
import com.abrid.dropme.service.dto.DestinationDTO;
import com.abrid.dropme.service.mapper.DestinationMapper;
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
 * Service Implementation for managing {@link Destination}.
 */
@Service
@Transactional
public class DestinationService {

    private final Logger log = LoggerFactory.getLogger(DestinationService.class);

    private final DestinationRepository destinationRepository;

    private final DestinationMapper destinationMapper;

    public DestinationService(DestinationRepository destinationRepository, DestinationMapper destinationMapper) {
        this.destinationRepository = destinationRepository;
        this.destinationMapper = destinationMapper;
    }

    /**
     * Save a destination.
     *
     * @param destinationDTO the entity to save.
     * @return the persisted entity.
     */
    public DestinationDTO save(DestinationDTO destinationDTO) {
        log.debug("Request to save Destination : {}", destinationDTO);
        Destination destination = destinationMapper.toEntity(destinationDTO);
        destination = destinationRepository.save(destination);
        return destinationMapper.toDto(destination);
    }

    /**
     * Get all the destinations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DestinationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Destinations");
        return destinationRepository.findAll(pageable)
            .map(destinationMapper::toDto);
    }


    /**
     *  Get all the destinations where Location is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<DestinationDTO> findAllWhereLocationIsNull() {
        log.debug("Request to get all destinations where Location is null");
        return StreamSupport
            .stream(destinationRepository.findAll().spliterator(), false)
            .filter(destination -> destination.getLocation() == null)
            .map(destinationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the destinations where Trip is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<DestinationDTO> findAllWhereTripIsNull() {
        log.debug("Request to get all destinations where Trip is null");
        return StreamSupport
            .stream(destinationRepository.findAll().spliterator(), false)
            .filter(destination -> destination.getTrip() == null)
            .map(destinationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one destination by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DestinationDTO> findOne(Long id) {
        log.debug("Request to get Destination : {}", id);
        return destinationRepository.findById(id)
            .map(destinationMapper::toDto);
    }

    /**
     * Delete the destination by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Destination : {}", id);
        destinationRepository.deleteById(id);
    }
}
