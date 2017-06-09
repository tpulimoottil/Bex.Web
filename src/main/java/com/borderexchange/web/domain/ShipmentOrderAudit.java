package com.borderexchange.web.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.borderexchange.web.domain.enumeration.ShipmentOrderStatus;

/**
 * A ShipmentOrderAudit.
 */
@Entity
@Table(name = "shipment_order_audit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShipmentOrderAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ShipmentOrderStatus status;

    @Column(name = "comments")
    private String comments;

    @Column(name = "additional_comments")
    private String additionalComments;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @ManyToOne
    private ShipmentOrder order;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShipmentOrderStatus getStatus() {
        return status;
    }

    public ShipmentOrderAudit status(ShipmentOrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ShipmentOrderStatus status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public ShipmentOrderAudit comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public ShipmentOrderAudit additionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
        return this;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public ShipmentOrderAudit createdOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public ShipmentOrder getOrder() {
        return order;
    }

    public ShipmentOrderAudit order(ShipmentOrder shipmentOrder) {
        this.order = shipmentOrder;
        return this;
    }

    public void setOrder(ShipmentOrder shipmentOrder) {
        this.order = shipmentOrder;
    }

    public User getUser() {
        return user;
    }

    public ShipmentOrderAudit user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShipmentOrderAudit shipmentOrderAudit = (ShipmentOrderAudit) o;
        if (shipmentOrderAudit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipmentOrderAudit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipmentOrderAudit{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", comments='" + getComments() + "'" +
            ", additionalComments='" + getAdditionalComments() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
