package com.borderexchange.web.service.mapper;

import com.borderexchange.web.domain.*;
import com.borderexchange.web.service.dto.ShipmentItemPropertiesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShipmentItemProperties and its DTO ShipmentItemPropertiesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShipmentItemPropertiesMapper extends EntityMapper <ShipmentItemPropertiesDTO, ShipmentItemProperties> {
    
    
    default ShipmentItemProperties fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShipmentItemProperties shipmentItemProperties = new ShipmentItemProperties();
        shipmentItemProperties.setId(id);
        return shipmentItemProperties;
    }
}
