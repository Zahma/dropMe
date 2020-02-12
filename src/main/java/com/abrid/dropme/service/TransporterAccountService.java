package com.abrid.dropme.service;

import com.abrid.dropme.domain.TransporterAccount;
import com.abrid.dropme.repository.TransporterAccountRepository;
import com.abrid.dropme.service.dto.TransporterAccountDTO;
import com.abrid.dropme.service.mapper.TransporterAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TransporterAccount}.
 */
@Service
@Transactional
public class TransporterAccountService {

    private final Logger log = LoggerFactory.getLogger(TransporterAccountService.class);

    private final TransporterAccountRepository transporterAccountRepository;

    private final TransporterAccountMapper transporterAccountMapper;

    public TransporterAccountService(TransporterAccountRepository transporterAccountRepository, TransporterAccountMapper transporterAccountMapper) {
        this.transporterAccountRepository = transporterAccountRepository;
        this.transporterAccountMapper = transporterAccountMapper;
    }

    /**
     * Save a transporterAccount.
     *
     * @param transporterAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public TransporterAccountDTO save(TransporterAccountDTO transporterAccountDTO) {
        log.debug("Request to save TransporterAccount : {}", transporterAccountDTO);
        TransporterAccount transporterAccount = transporterAccountMapper.toEntity(transporterAccountDTO);
        transporterAccount = transporterAccountRepository.save(transporterAccount);
        return transporterAccountMapper.toDto(transporterAccount);
    }

    /**
     * Get all the transporterAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TransporterAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransporterAccounts");
        return transporterAccountRepository.findAll(pageable)
            .map(transporterAccountMapper::toDto);
    }

    /**
     * Get one transporterAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransporterAccountDTO> findOne(Long id) {
        log.debug("Request to get TransporterAccount : {}", id);
        return transporterAccountRepository.findById(id)
            .map(transporterAccountMapper::toDto);
    }

    /**
     * Delete the transporterAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TransporterAccount : {}", id);
        transporterAccountRepository.deleteById(id);
    }
}
