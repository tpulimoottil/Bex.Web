package com.borderexchange.web.service.mapper;

import com.borderexchange.web.domain.*;
import com.borderexchange.web.service.dto.ShipmentOrderAuditDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShipmentOrderAudit and its DTO ShipmentOrderAuditDTO.
 */
@Mapper(componentModel = "spring", uses = {ShipmentOrderMapper.class, UserMapper.class, })
public interface ShipmentOrderAuditMapper extends EntityMapper <ShipmentOrderAuditDTO, ShipmentOrderAudit> {

    @Mapping(source = "order.id", target = "orderId")

    @Mapping(source = "user.id", target = "userId")
    ShipmentOrderAuditDTO toDto(ShipmentOrderAudit shipmentOrderAudit); 

    @Mapping(source = "orderId", target = "order")

    @Mapping(source = "userId", target = "user")
    ShipmentOrderAudit toEntity(ShipmentOrderAuditDTO shipmentOrderAuditDTO); 
    default ShipmentOrderAudit fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShipmentOrderAudit shipmentOrderAudit = new ShipmentOrderAudit();
        shipmentOrderAudit.setId(id);
        return shipmentOrderAudit;
    }
}
