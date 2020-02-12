package com.abrid.dropme.web.rest;

import com.abrid.dropme.service.DestinationService;
import com.abrid.dropme.web.rest.errors.BadRequestAlertException;
import com.abrid.dropme.service.dto.DestinationDTO;
import com.abrid.dropme.service.dto.DestinationCriteria;
import com.abrid.dropme.service.DestinationQueryService;

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
 * REST controller for managing {@link com.abrid.dropme.domain.Destination}.
 */
@RestController
@RequestMapping("/api")
public class DestinationResource {

    private final Logger log = LoggerFactory.getLogger(DestinationResource.class);

    private static final String ENTITY_NAME = "destination";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DestinationService destinationService;

    private final DestinationQueryService destinationQueryService;

    public DestinationResource(DestinationService destinationService, DestinationQueryService destinationQueryService) {
        this.destinationService = destinationService;
        this.destinationQueryService = destinationQueryService;
    }

    /**
     * {@code POST  /destinations} : Create a new destination.
     *
     * @param destinationDTO the destinationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new destinationDTO, or with status {@code 400 (Bad Request)} if the destination has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/destinations")
    public ResponseEntity<DestinationDTO> createDestination(@RequestBody DestinationDTO destinationDTO) throws URISyntaxException {
        log.debug("REST request to save Destination : {}", destinationDTO);
        if (destinationDTO.getId() != null) {
            throw new BadRequestAlertException("A new destination cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DestinationDTO result = destinationService.save(destinationDTO);
        return ResponseEntity.created(new URI("/api/destinations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /destinations} : Updates an existing destination.
     *
     * @param destinationDTO the destinationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated destinationDTO,
     * or with status {@code 400 (Bad Request)} if the destinationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the destinationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/destinations")
    public ResponseEntity<DestinationDTO> updateDestination(@RequestBody DestinationDTO destinationDTO) throws URISyntaxException {
        log.debug("REST request to update Destination : {}", destinationDTO);
        if (destinationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DestinationDTO result = destinationService.save(destinationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, destinationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /destinations} : get all the destinations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of destinations in body.
     */
    @GetMapping("/destinations")
    public ResponseEntity<List<DestinationDTO>> getAllDestinations(DestinationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Destinations by criteria: {}", criteria);
        Page<DestinationDTO> page = destinationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /destinations/count} : count all the destinations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/destinations/count")
    public ResponseEntity<Long> countDestinations(DestinationCriteria criteria) {
        log.debug("REST request to count Destinations by criteria: {}", criteria);
        return ResponseEntity.ok().body(destinationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /destinations/:id} : get the "id" destination.
     *
     * @param id the id of the destinationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the destinationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/destinations/{id}")
    public ResponseEntity<DestinationDTO> getDestination(@PathVariable Long id) {
        log.debug("REST request to get Destination : {}", id);
        Optional<DestinationDTO> destinationDTO = destinationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(destinationDTO);
    }

    /**
     * {@code DELETE  /destinations/:id} : delete the "id" destination.
     *
     * @param id the id of the destinationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/destinations/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        log.debug("REST request to delete Destination : {}", id);
        destinationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
