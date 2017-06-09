package com.borderexchange.web.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.borderexchange.web.domain.enumeration.ShipmentItemStatus;

/**
 * A DTO for the ShipmentItem entity.
 */
public class ShipmentItemDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String longDescription;

    private Long price;

    private ZonedDateTime freeStorageEndingDate;

    private ZonedDateTime createdOn;

    private ShipmentItemStatus status;

    private Long itemPropertiesId;

    private Long createdById;

    private Long userId;

    private Long categoryId;

    private Long orderId;

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

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ZonedDateTime getFreeStorageEndingDate() {
        return freeStorageEndingDate;
    }

    public void setFreeStorageEndingDate(ZonedDateTime freeStorageEndingDate) {
        this.freeStorageEndingDate = freeStorageEndingDate;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public ShipmentItemStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentItemStatus status) {
        this.status = status;
    }

    public Long getItemPropertiesId() {
        return itemPropertiesId;
    }

    public void setItemPropertiesId(Long shipmentItemPropertiesId) {
        this.itemPropertiesId = shipmentItemPropertiesId;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long userId) {
        this.createdById = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long shipmentItemCategoryId) {
        this.categoryId = shipmentItemCategoryId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long shipmentOrderId) {
        this.orderId = shipmentOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShipmentItemDTO shipmentItemDTO = (ShipmentItemDTO) o;
        if(shipmentItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipmentItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipmentItemDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", longDescription='" + getLongDescription() + "'" +
            ", price='" + getPrice() + "'" +
            ", freeStorageEndingDate='" + getFreeStorageEndingDate() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
