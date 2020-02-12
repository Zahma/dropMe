package com.abrid.dropme.web.rest;

import com.abrid.dropme.service.ReputationService;
import com.abrid.dropme.web.rest.errors.BadRequestAlertException;
import com.abrid.dropme.service.dto.ReputationDTO;
import com.abrid.dropme.service.dto.ReputationCriteria;
import com.abrid.dropme.service.ReputationQueryService;

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
 * REST controller for managing {@link com.abrid.dropme.domain.Reputation}.
 */
@RestController
@RequestMapping("/api")
public class ReputationResource {

    private final Logger log = LoggerFactory.getLogger(ReputationResource.class);

    private static final String ENTITY_NAME = "reputation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReputationService reputationService;

    private final ReputationQueryService reputationQueryService;

    public ReputationResource(ReputationService reputationService, ReputationQueryService reputationQueryService) {
        this.reputationService = reputationService;
        this.reputationQueryService = reputationQueryService;
    }

    /**
     * {@code POST  /reputations} : Create a new reputation.
     *
     * @param reputationDTO the reputationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reputationDTO, or with status {@code 400 (Bad Request)} if the reputation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reputations")
    public ResponseEntity<ReputationDTO> createReputation(@Valid @RequestBody ReputationDTO reputationDTO) throws URISyntaxException {
        log.debug("REST request to save Reputation : {}", reputationDTO);
        if (reputationDTO.getId() != null) {
            throw new BadRequestAlertException("A new reputation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReputationDTO result = reputationService.save(reputationDTO);
        return ResponseEntity.created(new URI("/api/reputations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reputations} : Updates an existing reputation.
     *
     * @param reputationDTO the reputationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reputationDTO,
     * or with status {@code 400 (Bad Request)} if the reputationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reputationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reputations")
    public ResponseEntity<ReputationDTO> updateReputation(@Valid @RequestBody ReputationDTO reputationDTO) throws URISyntaxException {
        log.debug("REST request to update Reputation : {}", reputationDTO);
        if (reputationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReputationDTO result = reputationService.save(reputationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reputationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reputations} : get all the reputations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reputations in body.
     */
    @GetMapping("/reputations")
    public ResponseEntity<List<ReputationDTO>> getAllReputations(ReputationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Reputations by criteria: {}", criteria);
        Page<ReputationDTO> page = reputationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reputations/count} : count all the reputations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/reputations/count")
    public ResponseEntity<Long> countReputations(ReputationCriteria criteria) {
        log.debug("REST request to count Reputations by criteria: {}", criteria);
        return ResponseEntity.ok().body(reputationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /reputations/:id} : get the "id" reputation.
     *
     * @param id the id of the reputationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reputationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reputations/{id}")
    public ResponseEntity<ReputationDTO> getReputation(@PathVariable Long id) {
        log.debug("REST request to get Reputation : {}", id);
        Optional<ReputationDTO> reputationDTO = reputationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reputationDTO);
    }

    /**
     * {@code DELETE  /reputations/:id} : delete the "id" reputation.
     *
     * @param id the id of the reputationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reputations/{id}")
    public ResponseEntity<Void> deleteReputation(@PathVariable Long id) {
        log.debug("REST request to delete Reputation : {}", id);
        reputationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
