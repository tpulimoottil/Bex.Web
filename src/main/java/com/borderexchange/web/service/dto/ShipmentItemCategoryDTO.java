package com.borderexchange.web.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ShipmentItemCategory entity.
 */
public class ShipmentItemCategoryDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long shipmentItemCategoryId) {
        this.parentId = shipmentItemCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShipmentItemCategoryDTO shipmentItemCategoryDTO = (ShipmentItemCategoryDTO) o;
        if(shipmentItemCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipmentItemCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipmentItemCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
