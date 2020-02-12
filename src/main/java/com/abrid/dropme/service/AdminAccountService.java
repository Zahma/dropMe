package com.abrid.dropme.service;

import com.abrid.dropme.domain.AdminAccount;
import com.abrid.dropme.repository.AdminAccountRepository;
import com.abrid.dropme.service.dto.AdminAccountDTO;
import com.abrid.dropme.service.mapper.AdminAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdminAccount}.
 */
@Service
@Transactional
public class AdminAccountService {

    private final Logger log = LoggerFactory.getLogger(AdminAccountService.class);

    private final AdminAccountRepository adminAccountRepository;

    private final AdminAccountMapper adminAccountMapper;

    public AdminAccountService(AdminAccountRepository adminAccountRepository, AdminAccountMapper adminAccountMapper) {
        this.adminAccountRepository = adminAccountRepository;
        this.adminAccountMapper = adminAccountMapper;
    }

    /**
     * Save a adminAccount.
     *
     * @param adminAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminAccountDTO save(AdminAccountDTO adminAccountDTO) {
        log.debug("Request to save AdminAccount : {}", adminAccountDTO);
        AdminAccount adminAccount = adminAccountMapper.toEntity(adminAccountDTO);
        adminAccount = adminAccountRepository.save(adminAccount);
        return adminAccountMapper.toDto(adminAccount);
    }

    /**
     * Get all the adminAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdminAccounts");
        return adminAccountRepository.findAll(pageable)
            .map(adminAccountMapper::toDto);
    }

    /**
     * Get one adminAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdminAccountDTO> findOne(Long id) {
        log.debug("Request to get AdminAccount : {}", id);
        return adminAccountRepository.findById(id)
            .map(adminAccountMapper::toDto);
    }

    /**
     * Delete the adminAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdminAccount : {}", id);
        adminAccountRepository.deleteById(id);
    }
}
