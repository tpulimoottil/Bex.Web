package com.borderexchange.web.service;

import com.borderexchange.web.service.dto.ShipmentItemCategoryDTO;
import java.util.List;

/**
 * Service Interface for managing ShipmentItemCategory.
 */
public interface ShipmentItemCategoryService {

    /**
     * Save a shipmentItemCategory.
     *
     * @param shipmentItemCategoryDTO the entity to save
     * @return the persisted entity
     */
    ShipmentItemCategoryDTO save(ShipmentItemCategoryDTO shipmentItemCategoryDTO);

    /**
     *  Get all the shipmentItemCategories.
     *
     *  @return the list of entities
     */
    List<ShipmentItemCategoryDTO> findAll();

    /**
     *  Get the "id" shipmentItemCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ShipmentItemCategoryDTO findOne(Long id);

    /**
     *  Delete the "id" shipmentItemCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
