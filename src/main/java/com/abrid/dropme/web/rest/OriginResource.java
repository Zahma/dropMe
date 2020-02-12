package com.abrid.dropme.web.rest;

import com.abrid.dropme.service.OriginService;
import com.abrid.dropme.web.rest.errors.BadRequestAlertException;
import com.abrid.dropme.service.dto.OriginDTO;
import com.abrid.dropme.service.dto.OriginCriteria;
import com.abrid.dropme.service.OriginQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.abrid.dropme.domain.Origin}.
 */
@RestController
@RequestMapping("/api")
public class OriginResource {

    private final Logger log = LoggerFactory.getLogger(OriginResource.class);

    private static final String ENTITY_NAME = "origin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OriginService originService;

    private final OriginQueryService originQueryService;

    public OriginResource(OriginService originService, OriginQueryService originQueryService) {
        this.originService = originService;
        this.originQueryService = originQueryService;
    }

    /**
     * {@code POST  /origins} : Create a new origin.
     *
     * @param originDTO the originDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new originDTO, or with status {@code 400 (Bad Request)} if the origin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/origins")
    public ResponseEntity<OriginDTO> createOrigin(@RequestBody OriginDTO originDTO) throws URISyntaxException {
        log.debug("REST request to save Origin : {}", originDTO);
        if (originDTO.getId() != null) {
            throw new BadRequestAlertException("A new origin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OriginDTO result = originService.save(originDTO);
        return ResponseEntity.created(new URI("/api/origins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /origins} : Updates an existing origin.
     *
     * @param originDTO the originDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated originDTO,
     * or with status {@code 400 (Bad Request)} if the originDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the originDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/origins")
    public ResponseEntity<OriginDTO> updateOrigin(@RequestBody OriginDTO originDTO) throws URISyntaxException {
        log.debug("REST request to update Origin : {}", originDTO);
        if (originDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OriginDTO result = originService.save(originDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, originDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /origins} : get all the origins.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of origins in body.
     */
    @GetMapping("/origins")
    public ResponseEntity<List<OriginDTO>> getAllOrigins(OriginCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Origins by criteria: {}", criteria);
        Page<OriginDTO> page = originQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /origins/count} : count all the origins.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/origins/count")
    public ResponseEntity<Long> countOrigins(OriginCriteria criteria) {
        log.debug("REST request to count Origins by criteria: {}", criteria);
        return ResponseEntity.ok().body(originQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /origins/:id} : get the "id" origin.
     *
     * @param id the id of the originDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the originDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/origins/{id}")
    public ResponseEntity<OriginDTO> getOrigin(@PathVariable Long id) {
        log.debug("REST request to get Origin : {}", id);
        Optional<OriginDTO> originDTO = originService.findOne(id);
        return ResponseUtil.wrapOrNotFound(originDTO);
    }

    /**
     * {@code DELETE  /origins/:id} : delete the "id" origin.
     *
     * @param id the id of the originDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/origins/{id}")
    public ResponseEntity<Void> deleteOrigin(@PathVariable Long id) {
        log.debug("REST request to delete Origin : {}", id);
        originService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
