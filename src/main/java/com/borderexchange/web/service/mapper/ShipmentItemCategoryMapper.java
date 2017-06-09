package com.borderexchange.web.service.mapper;

import com.borderexchange.web.domain.*;
import com.borderexchange.web.service.dto.ShipmentItemCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShipmentItemCategory and its DTO ShipmentItemCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShipmentItemCategoryMapper extends EntityMapper <ShipmentItemCategoryDTO, ShipmentItemCategory> {

    @Mapping(source = "parent.id", target = "parentId")
    ShipmentItemCategoryDTO toDto(ShipmentItemCategory shipmentItemCategory); 

    @Mapping(source = "parentId", target = "parent")
    ShipmentItemCategory toEntity(ShipmentItemCategoryDTO shipmentItemCategoryDTO); 
    default ShipmentItemCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShipmentItemCategory shipmentItemCategory = new ShipmentItemCategory();
        shipmentItemCategory.setId(id);
        return shipmentItemCategory;
    }
}
