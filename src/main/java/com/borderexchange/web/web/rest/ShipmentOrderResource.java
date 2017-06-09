package com.borderexchange.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.borderexchange.web.service.ShipmentOrderService;
import com.borderexchange.web.web.rest.util.HeaderUtil;
import com.borderexchange.web.web.rest.util.PaginationUtil;
import com.borderexchange.web.service.dto.ShipmentOrderDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ShipmentOrder.
 */
@RestController
@RequestMapping("/api")
public class ShipmentOrderResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentOrderResource.class);

    private static final String ENTITY_NAME = "shipmentOrder";

    private final ShipmentOrderService shipmentOrderService;

    public ShipmentOrderResource(ShipmentOrderService shipmentOrderService) {
        this.shipmentOrderService = shipmentOrderService;
    }

    /**
     * POST  /shipment-orders : Create a new shipmentOrder.
     *
     * @param shipmentOrderDTO the shipmentOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipmentOrderDTO, or with status 400 (Bad Request) if the shipmentOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipment-orders")
    @Timed
    public ResponseEntity<ShipmentOrderDTO> createShipmentOrder(@RequestBody ShipmentOrderDTO shipmentOrderDTO) throws URISyntaxException {
        log.debug("REST request to save ShipmentOrder : {}", shipmentOrderDTO);
        if (shipmentOrderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new shipmentOrder cannot already have an ID")).body(null);
        }
        ShipmentOrderDTO result = shipmentOrderService.save(shipmentOrderDTO);
        return ResponseEntity.created(new URI("/api/shipment-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipment-orders : Updates an existing shipmentOrder.
     *
     * @param shipmentOrderDTO the shipmentOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipmentOrderDTO,
     * or with status 400 (Bad Request) if the shipmentOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the shipmentOrderDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipment-orders")
    @Timed
    public ResponseEntity<ShipmentOrderDTO> updateShipmentOrder(@RequestBody ShipmentOrderDTO shipmentOrderDTO) throws URISyntaxException {
        log.debug("REST request to update ShipmentOrder : {}", shipmentOrderDTO);
        if (shipmentOrderDTO.getId() == null) {
            return createShipmentOrder(shipmentOrderDTO);
        }
        ShipmentOrderDTO result = shipmentOrderService.save(shipmentOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipment-orders : get all the shipmentOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shipmentOrders in body
     */
    @GetMapping("/shipment-orders")
    @Timed
    public ResponseEntity<List<ShipmentOrderDTO>> getAllShipmentOrders(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ShipmentOrders");
        Page<ShipmentOrderDTO> page = shipmentOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipment-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shipment-orders/:id : get the "id" shipmentOrder.
     *
     * @param id the id of the shipmentOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipmentOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shipment-orders/{id}")
    @Timed
    public ResponseEntity<ShipmentOrderDTO> getShipmentOrder(@PathVariable Long id) {
        log.debug("REST request to get ShipmentOrder : {}", id);
        ShipmentOrderDTO shipmentOrderDTO = shipmentOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shipmentOrderDTO));
    }

    /**
     * DELETE  /shipment-orders/:id : delete the "id" shipmentOrder.
     *
     * @param id the id of the shipmentOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipment-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteShipmentOrder(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentOrder : {}", id);
        shipmentOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
