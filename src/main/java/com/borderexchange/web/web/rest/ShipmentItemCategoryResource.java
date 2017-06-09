package com.borderexchange.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.borderexchange.web.service.ShipmentItemCategoryService;
import com.borderexchange.web.web.rest.util.HeaderUtil;
import com.borderexchange.web.service.dto.ShipmentItemCategoryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ShipmentItemCategory.
 */
@RestController
@RequestMapping("/api")
public class ShipmentItemCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentItemCategoryResource.class);

    private static final String ENTITY_NAME = "shipmentItemCategory";

    private final ShipmentItemCategoryService shipmentItemCategoryService;

    public ShipmentItemCategoryResource(ShipmentItemCategoryService shipmentItemCategoryService) {
        this.shipmentItemCategoryService = shipmentItemCategoryService;
    }

    /**
     * POST  /shipment-item-categories : Create a new shipmentItemCategory.
     *
     * @param shipmentItemCategoryDTO the shipmentItemCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipmentItemCategoryDTO, or with status 400 (Bad Request) if the shipmentItemCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipment-item-categories")
    @Timed
    public ResponseEntity<ShipmentItemCategoryDTO> createShipmentItemCategory(@RequestBody ShipmentItemCategoryDTO shipmentItemCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save ShipmentItemCategory : {}", shipmentItemCategoryDTO);
        if (shipmentItemCategoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new shipmentItemCategory cannot already have an ID")).body(null);
        }
        ShipmentItemCategoryDTO result = shipmentItemCategoryService.save(shipmentItemCategoryDTO);
        return ResponseEntity.created(new URI("/api/shipment-item-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipment-item-categories : Updates an existing shipmentItemCategory.
     *
     * @param shipmentItemCategoryDTO the shipmentItemCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipmentItemCategoryDTO,
     * or with status 400 (Bad Request) if the shipmentItemCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the shipmentItemCategoryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipment-item-categories")
    @Timed
    public ResponseEntity<ShipmentItemCategoryDTO> updateShipmentItemCategory(@RequestBody ShipmentItemCategoryDTO shipmentItemCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update ShipmentItemCategory : {}", shipmentItemCategoryDTO);
        if (shipmentItemCategoryDTO.getId() == null) {
            return createShipmentItemCategory(shipmentItemCategoryDTO);
        }
        ShipmentItemCategoryDTO result = shipmentItemCategoryService.save(shipmentItemCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentItemCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipment-item-categories : get all the shipmentItemCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shipmentItemCategories in body
     */
    @GetMapping("/shipment-item-categories")
    @Timed
    public List<ShipmentItemCategoryDTO> getAllShipmentItemCategories() {
        log.debug("REST request to get all ShipmentItemCategories");
        return shipmentItemCategoryService.findAll();
    }

    /**
     * GET  /shipment-item-categories/:id : get the "id" shipmentItemCategory.
     *
     * @param id the id of the shipmentItemCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipmentItemCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shipment-item-categories/{id}")
    @Timed
    public ResponseEntity<ShipmentItemCategoryDTO> getShipmentItemCategory(@PathVariable Long id) {
        log.debug("REST request to get ShipmentItemCategory : {}", id);
        ShipmentItemCategoryDTO shipmentItemCategoryDTO = shipmentItemCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shipmentItemCategoryDTO));
    }

    /**
     * DELETE  /shipment-item-categories/:id : delete the "id" shipmentItemCategory.
     *
     * @param id the id of the shipmentItemCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipment-item-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteShipmentItemCategory(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentItemCategory : {}", id);
        shipmentItemCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
