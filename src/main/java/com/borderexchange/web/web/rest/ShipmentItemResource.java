package com.borderexchange.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.borderexchange.web.service.ShipmentItemService;
import com.borderexchange.web.web.rest.util.HeaderUtil;
import com.borderexchange.web.web.rest.util.PaginationUtil;
import com.borderexchange.web.service.dto.ShipmentItemDTO;
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
 * REST controller for managing ShipmentItem.
 */
@RestController
@RequestMapping("/api")
public class ShipmentItemResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentItemResource.class);

    private static final String ENTITY_NAME = "shipmentItem";

    private final ShipmentItemService shipmentItemService;

    public ShipmentItemResource(ShipmentItemService shipmentItemService) {
        this.shipmentItemService = shipmentItemService;
    }

    /**
     * POST  /shipment-items : Create a new shipmentItem.
     *
     * @param shipmentItemDTO the shipmentItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipmentItemDTO, or with status 400 (Bad Request) if the shipmentItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipment-items")
    @Timed
    public ResponseEntity<ShipmentItemDTO> createShipmentItem(@RequestBody ShipmentItemDTO shipmentItemDTO) throws URISyntaxException {
        log.debug("REST request to save ShipmentItem : {}", shipmentItemDTO);
        if (shipmentItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new shipmentItem cannot already have an ID")).body(null);
        }
        ShipmentItemDTO result = shipmentItemService.save(shipmentItemDTO);
        return ResponseEntity.created(new URI("/api/shipment-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipment-items : Updates an existing shipmentItem.
     *
     * @param shipmentItemDTO the shipmentItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipmentItemDTO,
     * or with status 400 (Bad Request) if the shipmentItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the shipmentItemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipment-items")
    @Timed
    public ResponseEntity<ShipmentItemDTO> updateShipmentItem(@RequestBody ShipmentItemDTO shipmentItemDTO) throws URISyntaxException {
        log.debug("REST request to update ShipmentItem : {}", shipmentItemDTO);
        if (shipmentItemDTO.getId() == null) {
            return createShipmentItem(shipmentItemDTO);
        }
        ShipmentItemDTO result = shipmentItemService.save(shipmentItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipment-items : get all the shipmentItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shipmentItems in body
     */
    @GetMapping("/shipment-items")
    @Timed
    public ResponseEntity<List<ShipmentItemDTO>> getAllShipmentItems(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ShipmentItems");
        Page<ShipmentItemDTO> page = shipmentItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipment-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shipment-items/:id : get the "id" shipmentItem.
     *
     * @param id the id of the shipmentItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipmentItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shipment-items/{id}")
    @Timed
    public ResponseEntity<ShipmentItemDTO> getShipmentItem(@PathVariable Long id) {
        log.debug("REST request to get ShipmentItem : {}", id);
        ShipmentItemDTO shipmentItemDTO = shipmentItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shipmentItemDTO));
    }

    /**
     * DELETE  /shipment-items/:id : delete the "id" shipmentItem.
     *
     * @param id the id of the shipmentItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipment-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteShipmentItem(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentItem : {}", id);
        shipmentItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
