package com.abrid.dropme.service;

import com.abrid.dropme.domain.Origin;
import com.abrid.dropme.repository.OriginRepository;
import com.abrid.dropme.service.dto.OriginDTO;
import com.abrid.dropme.service.mapper.OriginMapper;
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
 * Service Implementation for managing {@link Origin}.
 */
@Service
@Transactional
public class OriginService {

    private final Logger log = LoggerFactory.getLogger(OriginService.class);

    private final OriginRepository originRepository;

    private final OriginMapper originMapper;

    public OriginService(OriginRepository originRepository, OriginMapper originMapper) {
        this.originRepository = originRepository;
        this.originMapper = originMapper;
    }

    /**
     * Save a origin.
     *
     * @param originDTO the entity to save.
     * @return the persisted entity.
     */
    public OriginDTO save(OriginDTO originDTO) {
        log.debug("Request to save Origin : {}", originDTO);
        Origin origin = originMapper.toEntity(originDTO);
        origin = originRepository.save(origin);
        return originMapper.toDto(origin);
    }

    /**
     * Get all the origins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OriginDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Origins");
        return originRepository.findAll(pageable)
            .map(originMapper::toDto);
    }


    /**
     *  Get all the origins where Location is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<OriginDTO> findAllWhereLocationIsNull() {
        log.debug("Request to get all origins where Location is null");
        return StreamSupport
            .stream(originRepository.findAll().spliterator(), false)
            .filter(origin -> origin.getLocation() == null)
            .map(originMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the origins where Trip is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<OriginDTO> findAllWhereTripIsNull() {
        log.debug("Request to get all origins where Trip is null");
        return StreamSupport
            .stream(originRepository.findAll().spliterator(), false)
            .filter(origin -> origin.getTrip() == null)
            .map(originMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one origin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OriginDTO> findOne(Long id) {
        log.debug("Request to get Origin : {}", id);
        return originRepository.findById(id)
            .map(originMapper::toDto);
    }

    /**
     * Delete the origin by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Origin : {}", id);
        originRepository.deleteById(id);
    }
}
