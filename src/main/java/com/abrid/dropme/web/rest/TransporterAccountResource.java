package com.abrid.dropme.web.rest;

import com.abrid.dropme.service.TransporterAccountService;
import com.abrid.dropme.web.rest.errors.BadRequestAlertException;
import com.abrid.dropme.service.dto.TransporterAccountDTO;
import com.abrid.dropme.service.dto.TransporterAccountCriteria;
import com.abrid.dropme.service.TransporterAccountQueryService;

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
 * REST controller for managing {@link com.abrid.dropme.domain.TransporterAccount}.
 */
@RestController
@RequestMapping("/api")
public class TransporterAccountResource {

    private final Logger log = LoggerFactory.getLogger(TransporterAccountResource.class);

    private static final String ENTITY_NAME = "transporterAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransporterAccountService transporterAccountService;

    private final TransporterAccountQueryService transporterAccountQueryService;

    public TransporterAccountResource(TransporterAccountService transporterAccountService, TransporterAccountQueryService transporterAccountQueryService) {
        this.transporterAccountService = transporterAccountService;
        this.transporterAccountQueryService = transporterAccountQueryService;
    }

    /**
     * {@code POST  /transporter-accounts} : Create a new transporterAccount.
     *
     * @param transporterAccountDTO the transporterAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transporterAccountDTO, or with status {@code 400 (Bad Request)} if the transporterAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transporter-accounts")
    public ResponseEntity<TransporterAccountDTO> createTransporterAccount(@Valid @RequestBody TransporterAccountDTO transporterAccountDTO) throws URISyntaxException {
        log.debug("REST request to save TransporterAccount : {}", transporterAccountDTO);
        if (transporterAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new transporterAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransporterAccountDTO result = transporterAccountService.save(transporterAccountDTO);
        return ResponseEntity.created(new URI("/api/transporter-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transporter-accounts} : Updates an existing transporterAccount.
     *
     * @param transporterAccountDTO the transporterAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transporterAccountDTO,
     * or with status {@code 400 (Bad Request)} if the transporterAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transporterAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transporter-accounts")
    public ResponseEntity<TransporterAccountDTO> updateTransporterAccount(@Valid @RequestBody TransporterAccountDTO transporterAccountDTO) throws URISyntaxException {
        log.debug("REST request to update TransporterAccount : {}", transporterAccountDTO);
        if (transporterAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransporterAccountDTO result = transporterAccountService.save(transporterAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transporterAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transporter-accounts} : get all the transporterAccounts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transporterAccounts in body.
     */
    @GetMapping("/transporter-accounts")
    public ResponseEntity<List<TransporterAccountDTO>> getAllTransporterAccounts(TransporterAccountCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TransporterAccounts by criteria: {}", criteria);
        Page<TransporterAccountDTO> page = transporterAccountQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transporter-accounts/count} : count all the transporterAccounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transporter-accounts/count")
    public ResponseEntity<Long> countTransporterAccounts(TransporterAccountCriteria criteria) {
        log.debug("REST request to count TransporterAccounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(transporterAccountQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transporter-accounts/:id} : get the "id" transporterAccount.
     *
     * @param id the id of the transporterAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transporterAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transporter-accounts/{id}")
    public ResponseEntity<TransporterAccountDTO> getTransporterAccount(@PathVariable Long id) {
        log.debug("REST request to get TransporterAccount : {}", id);
        Optional<TransporterAccountDTO> transporterAccountDTO = transporterAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transporterAccountDTO);
    }

    /**
     * {@code DELETE  /transporter-accounts/:id} : delete the "id" transporterAccount.
     *
     * @param id the id of the transporterAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transporter-accounts/{id}")
    public ResponseEntity<Void> deleteTransporterAccount(@PathVariable Long id) {
        log.debug("REST request to delete TransporterAccount : {}", id);
        transporterAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
