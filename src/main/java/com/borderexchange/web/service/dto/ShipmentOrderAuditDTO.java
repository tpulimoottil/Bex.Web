package com.borderexchange.web.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.borderexchange.web.domain.enumeration.ShipmentOrderStatus;

/**
 * A DTO for the ShipmentOrderAudit entity.
 */
public class ShipmentOrderAuditDTO implements Serializable {

    private Long id;

    private ShipmentOrderStatus status;

    private String comments;

    private String additionalComments;

    private ZonedDateTime createdOn;

    private Long orderId;

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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long shipmentOrderId) {
        this.orderId = shipmentOrderId;
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

        ShipmentOrderAuditDTO shipmentOrderAuditDTO = (ShipmentOrderAuditDTO) o;
        if(shipmentOrderAuditDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipmentOrderAuditDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipmentOrderAuditDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", comments='" + getComments() + "'" +
            ", additionalComments='" + getAdditionalComments() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
