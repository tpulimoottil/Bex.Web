package com.borderexchange.web.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.borderexchange.web.domain.enumeration.ShipmentOrderStatus;

/**
 * A DTO for the ShipmentOrder entity.
 */
public class ShipmentOrderDTO implements Serializable {

    private Long id;

    private ShipmentOrderStatus status;

    private ZonedDateTime createdOn;

    private Long amount;

    private Long shipmentAddressId;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShipmentOrderStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentOrderStatus status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getShipmentAddressId() {
        return shipmentAddressId;
    }

    public void setShipmentAddressId(Long shipmentAddressId) {
        this.shipmentAddressId = shipmentAddressId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShipmentOrderDTO shipmentOrderDTO = (ShipmentOrderDTO) o;
        if(shipmentOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipmentOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipmentOrderDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", amount='" + getAmount() + "'" +
            "}";
    }
}
