package com.abrid.dropme.service;

import com.abrid.dropme.domain.ClientAccount;
import com.abrid.dropme.repository.ClientAccountRepository;
import com.abrid.dropme.service.dto.ClientAccountDTO;
import com.abrid.dropme.service.mapper.ClientAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClientAccount}.
 */
@Service
@Transactional
public class ClientAccountService {

    private final Logger log = LoggerFactory.getLogger(ClientAccountService.class);

    private final ClientAccountRepository clientAccountRepository;

    private final ClientAccountMapper clientAccountMapper;

    public ClientAccountService(ClientAccountRepository clientAccountRepository, ClientAccountMapper clientAccountMapper) {
        this.clientAccountRepository = clientAccountRepository;
        this.clientAccountMapper = clientAccountMapper;
    }

    /**
     * Save a clientAccount.
     *
     * @param clientAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public ClientAccountDTO save(ClientAccountDTO clientAccountDTO) {
        log.debug("Request to save ClientAccount : {}", clientAccountDTO);
        ClientAccount clientAccount = clientAccountMapper.toEntity(clientAccountDTO);
        clientAccount = clientAccountRepository.save(clientAccount);
        return clientAccountMapper.toDto(clientAccount);
    }

    /**
     * Get all the clientAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClientAccounts");
        return clientAccountRepository.findAll(pageable)
            .map(clientAccountMapper::toDto);
    }

    /**
     * Get one clientAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientAccountDTO> findOne(Long id) {
        log.debug("Request to get ClientAccount : {}", id);
        return clientAccountRepository.findById(id)
            .map(clientAccountMapper::toDto);
    }

    /**
     * Delete the clientAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientAccount : {}", id);
        clientAccountRepository.deleteById(id);
    }
}
