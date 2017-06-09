package com.borderexchange.web.service;

import com.borderexchange.web.service.dto.ShipmentItemPropertiesDTO;
import java.util.List;

/**
 * Service Interface for managing ShipmentItemProperties.
 */
public interface ShipmentItemPropertiesService {

    /**
     * Save a shipmentItemProperties.
     *
     * @param shipmentItemPropertiesDTO the entity to save
     * @return the persisted entity
     */
    ShipmentItemPropertiesDTO save(ShipmentItemPropertiesDTO shipmentItemPropertiesDTO);

    /**
     *  Get all the shipmentItemProperties.
     *
     *  @return the list of entities
     */
    List<ShipmentItemPropertiesDTO> findAll();

    /**
     *  Get the "id" shipmentItemProperties.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ShipmentItemPropertiesDTO findOne(Long id);

    /**
     *  Delete the "id" shipmentItemProperties.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
