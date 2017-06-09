package com.borderexchange.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.borderexchange.web.service.ShipmentOrderAuditService;
import com.borderexchange.web.web.rest.util.HeaderUtil;
import com.borderexchange.web.service.dto.ShipmentOrderAuditDTO;
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
 * REST controller for managing ShipmentOrderAudit.
 */
@RestController
@RequestMapping("/api")
public class ShipmentOrderAuditResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentOrderAuditResource.class);

    private static final String ENTITY_NAME = "shipmentOrderAudit";

    private final ShipmentOrderAuditService shipmentOrderAuditService;

    public ShipmentOrderAuditResource(ShipmentOrderAuditService shipmentOrderAuditService) {
        this.shipmentOrderAuditService = shipmentOrderAuditService;
    }

    /**
     * POST  /shipment-order-audits : Create a new shipmentOrderAudit.
     *
     * @param shipmentOrderAuditDTO the shipmentOrderAuditDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipmentOrderAuditDTO, or with status 400 (Bad Request) if the shipmentOrderAudit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipment-order-audits")
    @Timed
    public ResponseEntity<ShipmentOrderAuditDTO> createShipmentOrderAudit(@RequestBody ShipmentOrderAuditDTO shipmentOrderAuditDTO) throws URISyntaxException {
        log.debug("REST request to save ShipmentOrderAudit : {}", shipmentOrderAuditDTO);
        if (shipmentOrderAuditDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new shipmentOrderAudit cannot already have an ID")).body(null);
        }
        ShipmentOrderAuditDTO result = shipmentOrderAuditService.save(shipmentOrderAuditDTO);
        return ResponseEntity.created(new URI("/api/shipment-order-audits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipment-order-audits : Updates an existing shipmentOrderAudit.
     *
     * @param shipmentOrderAuditDTO the shipmentOrderAuditDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipmentOrderAuditDTO,
     * or with status 400 (Bad Request) if the shipmentOrderAuditDTO is not valid,
     * or with status 500 (Internal Server Error) if the shipmentOrderAuditDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipment-order-audits")
    @Timed
    public ResponseEntity<ShipmentOrderAuditDTO> updateShipmentOrderAudit(@RequestBody ShipmentOrderAuditDTO shipmentOrderAuditDTO) throws URISyntaxException {
        log.debug("REST request to update ShipmentOrderAudit : {}", shipmentOrderAuditDTO);
        if (shipmentOrderAuditDTO.getId() == null) {
            return createShipmentOrderAudit(shipmentOrderAuditDTO);
        }
        ShipmentOrderAuditDTO result = shipmentOrderAuditService.save(shipmentOrderAuditDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentOrderAuditDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipment-order-audits : get all the shipmentOrderAudits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shipmentOrderAudits in body
     */
    @GetMapping("/shipment-order-audits")
    @Timed
    public List<ShipmentOrderAuditDTO> getAllShipmentOrderAudits() {
        log.debug("REST request to get all ShipmentOrderAudits");
        return shipmentOrderAuditService.findAll();
    }

    /**
     * GET  /shipment-order-audits/:id : get the "id" shipmentOrderAudit.
     *
     * @param id the id of the shipmentOrderAuditDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipmentOrderAuditDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shipment-order-audits/{id}")
    @Timed
    public ResponseEntity<ShipmentOrderAuditDTO> getShipmentOrderAudit(@PathVariable Long id) {
        log.debug("REST request to get ShipmentOrderAudit : {}", id);
        ShipmentOrderAuditDTO shipmentOrderAuditDTO = shipmentOrderAuditService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shipmentOrderAuditDTO));
    }

    /**
     * DELETE  /shipment-order-audits/:id : delete the "id" shipmentOrderAudit.
     *
     * @param id the id of the shipmentOrderAuditDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipment-order-audits/{id}")
    @Timed
    public ResponseEntity<Void> deleteShipmentOrderAudit(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentOrderAudit : {}", id);
        shipmentOrderAuditService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
