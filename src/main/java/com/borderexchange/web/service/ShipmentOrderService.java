package com.borderexchange.web.service;

import com.borderexchange.web.service.dto.ShipmentOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ShipmentOrder.
 */
public interface ShipmentOrderService {

    /**
     * Save a shipmentOrder.
     *
     * @param shipmentOrderDTO the entity to save
     * @return the persisted entity
     */
    ShipmentOrderDTO save(ShipmentOrderDTO shipmentOrderDTO);

    /**
     *  Get all the shipmentOrders.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ShipmentOrderDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" shipmentOrder.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ShipmentOrderDTO findOne(Long id);

    /**
     *  Delete the "id" shipmentOrder.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
