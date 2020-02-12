package com.abrid.dropme.web.rest;

import com.abrid.dropme.service.ClientAccountService;
import com.abrid.dropme.web.rest.errors.BadRequestAlertException;
import com.abrid.dropme.service.dto.ClientAccountDTO;
import com.abrid.dropme.service.dto.ClientAccountCriteria;
import com.abrid.dropme.service.ClientAccountQueryService;

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
 * REST controller for managing {@link com.abrid.dropme.domain.ClientAccount}.
 */
@RestController
@RequestMapping("/api")
public class ClientAccountResource {

    private final Logger log = LoggerFactory.getLogger(ClientAccountResource.class);

    private static final String ENTITY_NAME = "clientAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientAccountService clientAccountService;

    private final ClientAccountQueryService clientAccountQueryService;

    public ClientAccountResource(ClientAccountService clientAccountService, ClientAccountQueryService clientAccountQueryService) {
        this.clientAccountService = clientAccountService;
        this.clientAccountQueryService = clientAccountQueryService;
    }

    /**
     * {@code POST  /client-accounts} : Create a new clientAccount.
     *
     * @param clientAccountDTO the clientAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientAccountDTO, or with status {@code 400 (Bad Request)} if the clientAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-accounts")
    public ResponseEntity<ClientAccountDTO> createClientAccount(@Valid @RequestBody ClientAccountDTO clientAccountDTO) throws URISyntaxException {
        log.debug("REST request to save ClientAccount : {}", clientAccountDTO);
        if (clientAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientAccountDTO result = clientAccountService.save(clientAccountDTO);
        return ResponseEntity.created(new URI("/api/client-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-accounts} : Updates an existing clientAccount.
     *
     * @param clientAccountDTO the clientAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientAccountDTO,
     * or with status {@code 400 (Bad Request)} if the clientAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-accounts")
    public ResponseEntity<ClientAccountDTO> updateClientAccount(@Valid @RequestBody ClientAccountDTO clientAccountDTO) throws URISyntaxException {
        log.debug("REST request to update ClientAccount : {}", clientAccountDTO);
        if (clientAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientAccountDTO result = clientAccountService.save(clientAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /client-accounts} : get all the clientAccounts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientAccounts in body.
     */
    @GetMapping("/client-accounts")
    public ResponseEntity<List<ClientAccountDTO>> getAllClientAccounts(ClientAccountCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ClientAccounts by criteria: {}", criteria);
        Page<ClientAccountDTO> page = clientAccountQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /client-accounts/count} : count all the clientAccounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/client-accounts/count")
    public ResponseEntity<Long> countClientAccounts(ClientAccountCriteria criteria) {
        log.debug("REST request to count ClientAccounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(clientAccountQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /client-accounts/:id} : get the "id" clientAccount.
     *
     * @param id the id of the clientAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-accounts/{id}")
    public ResponseEntity<ClientAccountDTO> getClientAccount(@PathVariable Long id) {
        log.debug("REST request to get ClientAccount : {}", id);
        Optional<ClientAccountDTO> clientAccountDTO = clientAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientAccountDTO);
    }

    /**
     * {@code DELETE  /client-accounts/:id} : delete the "id" clientAccount.
     *
     * @param id the id of the clientAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-accounts/{id}")
    public ResponseEntity<Void> deleteClientAccount(@PathVariable Long id) {
        log.debug("REST request to delete ClientAccount : {}", id);
        clientAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
