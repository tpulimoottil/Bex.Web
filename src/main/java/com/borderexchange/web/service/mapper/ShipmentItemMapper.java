package com.borderexchange.web.service.mapper;

import com.borderexchange.web.domain.*;
import com.borderexchange.web.service.dto.ShipmentItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShipmentItem and its DTO ShipmentItemDTO.
 */
@Mapper(componentModel = "spring", uses = {ShipmentItemPropertiesMapper.class, UserMapper.class, ShipmentItemCategoryMapper.class, ShipmentOrderMapper.class, })
public interface ShipmentItemMapper extends EntityMapper <ShipmentItemDTO, ShipmentItem> {

    @Mapping(source = "itemProperties.id", target = "itemPropertiesId")

    @Mapping(source = "createdBy.id", target = "createdById")

    @Mapping(source = "user.id", target = "userId")

    @Mapping(source = "category.id", target = "categoryId")

    @Mapping(source = "order.id", target = "orderId")
    ShipmentItemDTO toDto(ShipmentItem shipmentItem); 

    @Mapping(source = "itemPropertiesId", target = "itemProperties")

    @Mapping(source = "createdById", target = "createdBy")

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "categoryId", target = "category")

    @Mapping(source = "orderId", target = "order")
    ShipmentItem toEntity(ShipmentItemDTO shipmentItemDTO); 
    default ShipmentItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShipmentItem shipmentItem = new ShipmentItem();
        shipmentItem.setId(id);
        return shipmentItem;
    }
}
