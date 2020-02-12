package com.abrid.dropme.service;

import com.abrid.dropme.domain.Reputation;
import com.abrid.dropme.repository.ReputationRepository;
import com.abrid.dropme.service.dto.ReputationDTO;
import com.abrid.dropme.service.mapper.ReputationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Reputation}.
 */
@Service
@Transactional
public class ReputationService {

    private final Logger log = LoggerFactory.getLogger(ReputationService.class);

    private final ReputationRepository reputationRepository;

    private final ReputationMapper reputationMapper;

    public ReputationService(ReputationRepository reputationRepository, ReputationMapper reputationMapper) {
        this.reputationRepository = reputationRepository;
        this.reputationMapper = reputationMapper;
    }

    /**
     * Save a reputation.
     *
     * @param reputationDTO the entity to save.
     * @return the persisted entity.
     */
    public ReputationDTO save(ReputationDTO reputationDTO) {
        log.debug("Request to save Reputation : {}", reputationDTO);
        Reputation reputation = reputationMapper.toEntity(reputationDTO);
        reputation = reputationRepository.save(reputation);
        return reputationMapper.toDto(reputation);
    }

    /**
     * Get all the reputations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReputationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reputations");
        return reputationRepository.findAll(pageable)
            .map(reputationMapper::toDto);
    }

    /**
     * Get one reputation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReputationDTO> findOne(Long id) {
        log.debug("Request to get Reputation : {}", id);
        return reputationRepository.findById(id)
            .map(reputationMapper::toDto);
    }

    /**
     * Delete the reputation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Reputation : {}", id);
        reputationRepository.deleteById(id);
    }
}
