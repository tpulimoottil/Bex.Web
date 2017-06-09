package com.borderexchange.web.service;

import com.borderexchange.web.service.dto.ShipmentAddressDTO;
import java.util.List;

/**
 * Service Interface for managing ShipmentAddress.
 */
public interface ShipmentAddressService {

    /**
     * Save a shipmentAddress.
     *
     * @param shipmentAddressDTO the entity to save
     * @return the persisted entity
     */
    ShipmentAddressDTO save(ShipmentAddressDTO shipmentAddressDTO);

    /**
     *  Get all the shipmentAddresses.
     *
     *  @return the list of entities
     */
    List<ShipmentAddressDTO> findAll();

    /**
     *  Get the "id" shipmentAddress.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ShipmentAddressDTO findOne(Long id);

    /**
     *  Delete the "id" shipmentAddress.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
