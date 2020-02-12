package com.abrid.dropme.web.rest;

import com.abrid.dropme.service.AdminAccountService;
import com.abrid.dropme.web.rest.errors.BadRequestAlertException;
import com.abrid.dropme.service.dto.AdminAccountDTO;
import com.abrid.dropme.service.dto.AdminAccountCriteria;
import com.abrid.dropme.service.AdminAccountQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.abrid.dropme.domain.AdminAccount}.
 */
@RestController
@RequestMapping("/api")
public class AdminAccountResource {

    private final Logger log = LoggerFactory.getLogger(AdminAccountResource.class);

    private static final String ENTITY_NAME = "adminAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminAccountService adminAccountService;

    private final AdminAccountQueryService adminAccountQueryService;

    public AdminAccountResource(AdminAccountService adminAccountService, AdminAccountQueryService adminAccountQueryService) {
        this.adminAccountService = adminAccountService;
        this.adminAccountQueryService = adminAccountQueryService;
    }

    /**
     * {@code POST  /admin-accounts} : Create a new adminAccount.
     *
     * @param adminAccountDTO the adminAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminAccountDTO, or with status {@code 400 (Bad Request)} if the adminAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-accounts")
    public ResponseEntity<AdminAccountDTO> createAdminAccount(@Valid @RequestBody AdminAccountDTO adminAccountDTO) throws URISyntaxException {
        log.debug("REST request to save AdminAccount : {}", adminAccountDTO);
        if (adminAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new adminAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminAccountDTO result = adminAccountService.save(adminAccountDTO);
        return ResponseEntity.created(new URI("/api/admin-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-accounts} : Updates an existing adminAccount.
     *
     * @param adminAccountDTO the adminAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminAccountDTO,
     * or with status {@code 400 (Bad Request)} if the adminAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-accounts")
    public ResponseEntity<AdminAccountDTO> updateAdminAccount(@Valid @RequestBody AdminAccountDTO adminAccountDTO) throws URISyntaxException {
        log.debug("REST request to update AdminAccount : {}", adminAccountDTO);
        if (adminAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdminAccountDTO result = adminAccountService.save(adminAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /admin-accounts} : get all the adminAccounts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminAccounts in body.
     */
    @GetMapping("/admin-accounts")
    public ResponseEntity<List<AdminAccountDTO>> getAllAdminAccounts(AdminAccountCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdminAccounts by criteria: {}", criteria);
        Page<AdminAccountDTO> page = adminAccountQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admin-accounts/count} : count all the adminAccounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/admin-accounts/count")
    public ResponseEntity<Long> countAdminAccounts(AdminAccountCriteria criteria) {
        log.debug("REST request to count AdminAccounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(adminAccountQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /admin-accounts/:id} : get the "id" adminAccount.
     *
     * @param id the id of the adminAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-accounts/{id}")
    public ResponseEntity<AdminAccountDTO> getAdminAccount(@PathVariable Long id) {
        log.debug("REST request to get AdminAccount : {}", id);
        Optional<AdminAccountDTO> adminAccountDTO = adminAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminAccountDTO);
    }

    /**
     * {@code DELETE  /admin-accounts/:id} : delete the "id" adminAccount.
     *
     * @param id the id of the adminAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-accounts/{id}")
    public ResponseEntity<Void> deleteAdminAccount(@PathVariable Long id) {
        log.debug("REST request to delete AdminAccount : {}", id);
        adminAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
