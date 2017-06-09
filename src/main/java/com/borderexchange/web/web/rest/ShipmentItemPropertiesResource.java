package com.borderexchange.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.borderexchange.web.service.ShipmentItemPropertiesService;
import com.borderexchange.web.web.rest.util.HeaderUtil;
import com.borderexchange.web.service.dto.ShipmentItemPropertiesDTO;
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
 * REST controller for managing ShipmentItemProperties.
 */
@RestController
@RequestMapping("/api")
public class ShipmentItemPropertiesResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentItemPropertiesResource.class);

    private static final String ENTITY_NAME = "shipmentItemProperties";

    private final ShipmentItemPropertiesService shipmentItemPropertiesService;

    public ShipmentItemPropertiesResource(ShipmentItemPropertiesService shipmentItemPropertiesService) {
        this.shipmentItemPropertiesService = shipmentItemPropertiesService;
    }

    /**
     * POST  /shipment-item-properties : Create a new shipmentItemProperties.
     *
     * @param shipmentItemPropertiesDTO the shipmentItemPropertiesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipmentItemPropertiesDTO, or with status 400 (Bad Request) if the shipmentItemProperties has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipment-item-properties")
    @Timed
    public ResponseEntity<ShipmentItemPropertiesDTO> createShipmentItemProperties(@RequestBody ShipmentItemPropertiesDTO shipmentItemPropertiesDTO) throws URISyntaxException {
        log.debug("REST request to save ShipmentItemProperties : {}", shipmentItemPropertiesDTO);
        if (shipmentItemPropertiesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new shipmentItemProperties cannot already have an ID")).body(null);
        }
        ShipmentItemPropertiesDTO result = shipmentItemPropertiesService.save(shipmentItemPropertiesDTO);
        return ResponseEntity.created(new URI("/api/shipment-item-properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipment-item-properties : Updates an existing shipmentItemProperties.
     *
     * @param shipmentItemPropertiesDTO the shipmentItemPropertiesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipmentItemPropertiesDTO,
     * or with status 400 (Bad Request) if the shipmentItemPropertiesDTO is not valid,
     * or with status 500 (Internal Server Error) if the shipmentItemPropertiesDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipment-item-properties")
    @Timed
    public ResponseEntity<ShipmentItemPropertiesDTO> updateShipmentItemProperties(@RequestBody ShipmentItemPropertiesDTO shipmentItemPropertiesDTO) throws URISyntaxException {
        log.debug("REST request to update ShipmentItemProperties : {}", shipmentItemPropertiesDTO);
        if (shipmentItemPropertiesDTO.getId() == null) {
            return createShipmentItemProperties(shipmentItemPropertiesDTO);
        }
        ShipmentItemPropertiesDTO result = shipmentItemPropertiesService.save(shipmentItemPropertiesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentItemPropertiesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipment-item-properties : get all the shipmentItemProperties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shipmentItemProperties in body
     */
    @GetMapping("/shipment-item-properties")
    @Timed
    public List<ShipmentItemPropertiesDTO> getAllShipmentItemProperties() {
        log.debug("REST request to get all ShipmentItemProperties");
        return shipmentItemPropertiesService.findAll();
    }

    /**
     * GET  /shipment-item-properties/:id : get the "id" shipmentItemProperties.
     *
     * @param id the id of the shipmentItemPropertiesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipmentItemPropertiesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shipment-item-properties/{id}")
    @Timed
    public ResponseEntity<ShipmentItemPropertiesDTO> getShipmentItemProperties(@PathVariable Long id) {
        log.debug("REST request to get ShipmentItemProperties : {}", id);
        ShipmentItemPropertiesDTO shipmentItemPropertiesDTO = shipmentItemPropertiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shipmentItemPropertiesDTO));
    }

    /**
     * DELETE  /shipment-item-properties/:id : delete the "id" shipmentItemProperties.
     *
     * @param id the id of the shipmentItemPropertiesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipment-item-properties/{id}")
    @Timed
    public ResponseEntity<Void> deleteShipmentItemProperties(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentItemProperties : {}", id);
        shipmentItemPropertiesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
