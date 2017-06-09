package com.borderexchange.web.service.mapper;

import com.borderexchange.web.domain.*;
import com.borderexchange.web.service.dto.ShipmentOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShipmentOrder and its DTO ShipmentOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {ShipmentAddressMapper.class, UserMapper.class, })
public interface ShipmentOrderMapper extends EntityMapper <ShipmentOrderDTO, ShipmentOrder> {

    @Mapping(source = "shipmentAddress.id", target = "shipmentAddressId")

    @Mapping(source = "user.id", target = "userId")
    ShipmentOrderDTO toDto(ShipmentOrder shipmentOrder); 

    @Mapping(source = "shipmentAddressId", target = "shipmentAddress")

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "audits", ignore = true)
    @Mapping(target = "shipmentItems", ignore = true)
    ShipmentOrder toEntity(ShipmentOrderDTO shipmentOrderDTO); 
    default ShipmentOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShipmentOrder shipmentOrder = new ShipmentOrder();
        shipmentOrder.setId(id);
        return shipmentOrder;
    }
}
