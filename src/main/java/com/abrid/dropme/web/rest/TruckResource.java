package com.abrid.dropme.web.rest;

import com.abrid.dropme.service.TruckService;
import com.abrid.dropme.web.rest.errors.BadRequestAlertException;
import com.abrid.dropme.service.dto.TruckDTO;
import com.abrid.dropme.service.dto.TruckCriteria;
import com.abrid.dropme.service.TruckQueryService;

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

/**
 * REST controller for managing {@link com.abrid.dropme.domain.Truck}.
 */
@RestController
@RequestMapping("/api")
public class TruckResource {

    private final Logger log = LoggerFactory.getLogger(TruckResource.class);

    private static final String ENTITY_NAME = "truck";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TruckService truckService;

    private final TruckQueryService truckQueryService;

    public TruckResource(TruckService truckService, TruckQueryService truckQueryService) {
        this.truckService = truckService;
        this.truckQueryService = truckQueryService;
    }

    /**
     * {@code POST  /trucks} : Create a new truck.
     *
     * @param truckDTO the truckDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new truckDTO, or with status {@code 400 (Bad Request)} if the truck has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trucks")
    public ResponseEntity<TruckDTO> createTruck(@RequestBody TruckDTO truckDTO) throws URISyntaxException {
        log.debug("REST request to save Truck : {}", truckDTO);
        if (truckDTO.getId() != null) {
            throw new BadRequestAlertException("A new truck cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TruckDTO result = truckService.save(truckDTO);
        return ResponseEntity.created(new URI("/api/trucks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trucks} : Updates an existing truck.
     *
     * @param truckDTO the truckDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated truckDTO,
     * or with status {@code 400 (Bad Request)} if the truckDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the truckDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trucks")
    public ResponseEntity<TruckDTO> updateTruck(@RequestBody TruckDTO truckDTO) throws URISyntaxException {
        log.debug("REST request to update Truck : {}", truckDTO);
        if (truckDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TruckDTO result = truckService.save(truckDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, truckDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trucks} : get all the trucks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trucks in body.
     */
    @GetMapping("/trucks")
    public ResponseEntity<List<TruckDTO>> getAllTrucks(TruckCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Trucks by criteria: {}", criteria);
        Page<TruckDTO> page = truckQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trucks/count} : count all the trucks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/trucks/count")
    public ResponseEntity<Long> countTrucks(TruckCriteria criteria) {
        log.debug("REST request to count Trucks by criteria: {}", criteria);
        return ResponseEntity.ok().body(truckQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /trucks/:id} : get the "id" truck.
     *
     * @param id the id of the truckDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the truckDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trucks/{id}")
    public ResponseEntity<TruckDTO> getTruck(@PathVariable Long id) {
        log.debug("REST request to get Truck : {}", id);
        Optional<TruckDTO> truckDTO = truckService.findOne(id);
        return ResponseUtil.wrapOrNotFound(truckDTO);
    }

    /**
     * {@code DELETE  /trucks/:id} : delete the "id" truck.
     *
     * @param id the id of the truckDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trucks/{id}")
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        log.debug("REST request to delete Truck : {}", id);
        truckService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
