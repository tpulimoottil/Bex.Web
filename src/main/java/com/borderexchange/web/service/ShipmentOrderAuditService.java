package com.borderexchange.web.service;

import com.borderexchange.web.service.dto.ShipmentOrderAuditDTO;
import java.util.List;

/**
 * Service Interface for managing ShipmentOrderAudit.
 */
public interface ShipmentOrderAuditService {

    /**
     * Save a shipmentOrderAudit.
     *
     * @param shipmentOrderAuditDTO the entity to save
     * @return the persisted entity
     */
    ShipmentOrderAuditDTO save(ShipmentOrderAuditDTO shipmentOrderAuditDTO);

    /**
     *  Get all the shipmentOrderAudits.
     *
     *  @return the list of entities
     */
    List<ShipmentOrderAuditDTO> findAll();

    /**
     *  Get the "id" shipmentOrderAudit.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ShipmentOrderAuditDTO findOne(Long id);

    /**
     *  Delete the "id" shipmentOrderAudit.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
