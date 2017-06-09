package com.borderexchange.web.service.mapper;

import com.borderexchange.web.domain.*;
import com.borderexchange.web.service.dto.ShipmentAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShipmentAddress and its DTO ShipmentAddressDTO.
 */
@Mapper(componentModel = "spring", uses = {StateMapper.class, })
public interface ShipmentAddressMapper extends EntityMapper <ShipmentAddressDTO, ShipmentAddress> {

    @Mapping(source = "state.id", target = "stateId")
    ShipmentAddressDTO toDto(ShipmentAddress shipmentAddress); 

    @Mapping(source = "stateId", target = "state")
    ShipmentAddress toEntity(ShipmentAddressDTO shipmentAddressDTO); 
    default ShipmentAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShipmentAddress shipmentAddress = new ShipmentAddress();
        shipmentAddress.setId(id);
        return shipmentAddress;
    }
}
