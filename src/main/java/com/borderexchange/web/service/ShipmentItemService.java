package com.borderexchange.web.service;

import com.borderexchange.web.service.dto.ShipmentItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ShipmentItem.
 */
public interface ShipmentItemService {

    /**
     * Save a shipmentItem.
     *
     * @param shipmentItemDTO the entity to save
     * @return the persisted entity
     */
    ShipmentItemDTO save(ShipmentItemDTO shipmentItemDTO);

    /**
     *  Get all the shipmentItems.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ShipmentItemDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" shipmentItem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ShipmentItemDTO findOne(Long id);

    /**
     *  Delete the "id" shipmentItem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
