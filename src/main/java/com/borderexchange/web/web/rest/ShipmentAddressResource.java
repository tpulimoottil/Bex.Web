package com.borderexchange.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.borderexchange.web.service.ShipmentAddressService;
import com.borderexchange.web.web.rest.util.HeaderUtil;
import com.borderexchange.web.service.dto.ShipmentAddressDTO;
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
 * REST controller for managing ShipmentAddress.
 */
@RestController
@RequestMapping("/api")
public class ShipmentAddressResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentAddressResource.class);

    private static final String ENTITY_NAME = "shipmentAddress";

    private final ShipmentAddressService shipmentAddressService;

    public ShipmentAddressResource(ShipmentAddressService shipmentAddressService) {
        this.shipmentAddressService = shipmentAddressService;
    }

    /**
     * POST  /shipment-addresses : Create a new shipmentAddress.
     *
     * @param shipmentAddressDTO the shipmentAddressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipmentAddressDTO, or with status 400 (Bad Request) if the shipmentAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipment-addresses")
    @Timed
    public ResponseEntity<ShipmentAddressDTO> createShipmentAddress(@RequestBody ShipmentAddressDTO shipmentAddressDTO) throws URISyntaxException {
        log.debug("REST request to save ShipmentAddress : {}", shipmentAddressDTO);
        if (shipmentAddressDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new shipmentAddress cannot already have an ID")).body(null);
        }
        ShipmentAddressDTO result = shipmentAddressService.save(shipmentAddressDTO);
        return ResponseEntity.created(new URI("/api/shipment-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipment-addresses : Updates an existing shipmentAddress.
     *
     * @param shipmentAddressDTO the shipmentAddressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipmentAddressDTO,
     * or with status 400 (Bad Request) if the shipmentAddressDTO is not valid,
     * or with status 500 (Internal Server Error) if the shipmentAddressDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipment-addresses")
    @Timed
    public ResponseEntity<ShipmentAddressDTO> updateShipmentAddress(@RequestBody ShipmentAddressDTO shipmentAddressDTO) throws URISyntaxException {
        log.debug("REST request to update ShipmentAddress : {}", shipmentAddressDTO);
        if (shipmentAddressDTO.getId() == null) {
            return createShipmentAddress(shipmentAddressDTO);
        }
        ShipmentAddressDTO result = shipmentAddressService.save(shipmentAddressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipment-addresses : get all the shipmentAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shipmentAddresses in body
     */
    @GetMapping("/shipment-addresses")
    @Timed
    public List<ShipmentAddressDTO> getAllShipmentAddresses() {
        log.debug("REST request to get all ShipmentAddresses");
        return shipmentAddressService.findAll();
    }

    /**
     * GET  /shipment-addresses/:id : get the "id" shipmentAddress.
     *
     * @param id the id of the shipmentAddressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipmentAddressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shipment-addresses/{id}")
    @Timed
    public ResponseEntity<ShipmentAddressDTO> getShipmentAddress(@PathVariable Long id) {
        log.debug("REST request to get ShipmentAddress : {}", id);
        ShipmentAddressDTO shipmentAddressDTO = shipmentAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shipmentAddressDTO));
    }

    /**
     * DELETE  /shipment-addresses/:id : delete the "id" shipmentAddress.
     *
     * @param id the id of the shipmentAddressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipment-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteShipmentAddress(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentAddress : {}", id);
        shipmentAddressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
