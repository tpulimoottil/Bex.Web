package com.borderexchange.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.borderexchange.web.domain.enumeration.ShipmentOrderStatus;

/**
 * A ShipmentOrder.
 */
@Entity
@Table(name = "shipment_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShipmentOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ShipmentOrderStatus status;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "amount")
    private Long amount;

    @OneToOne
    @JoinColumn(unique = true)
    private ShipmentAddress shipmentAddress;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShipmentOrderAudit> audits = new HashSet<>();

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShipmentItem> shipmentItems = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShipmentOrderStatus getStatus() {
        return status;
    }

    public ShipmentOrder status(ShipmentOrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ShipmentOrderStatus status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public ShipmentOrder createdOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getAmount() {
        return amount;
    }

    public ShipmentOrder amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public ShipmentAddress getShipmentAddress() {
        return shipmentAddress;
    }

    public ShipmentOrder shipmentAddress(ShipmentAddress shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
        return this;
    }

    public void setShipmentAddress(ShipmentAddress shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    public User getUser() {
        return user;
    }

    public ShipmentOrder user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ShipmentOrderAudit> getAudits() {
        return audits;
    }

    public ShipmentOrder audits(Set<ShipmentOrderAudit> shipmentOrderAudits) {
        this.audits = shipmentOrderAudits;
        return this;
    }

    public ShipmentOrder addAudits(ShipmentOrderAudit shipmentOrderAudit) {
        this.audits.add(shipmentOrderAudit);
        shipmentOrderAudit.setOrder(this);
        return this;
    }

    public ShipmentOrder removeAudits(ShipmentOrderAudit shipmentOrderAudit) {
        this.audits.remove(shipmentOrderAudit);
        shipmentOrderAudit.setOrder(null);
        return this;
    }

    public void setAudits(Set<ShipmentOrderAudit> shipmentOrderAudits) {
        this.audits = shipmentOrderAudits;
    }

    public Set<ShipmentItem> getShipmentItems() {
        return shipmentItems;
    }

    public ShipmentOrder shipmentItems(Set<ShipmentItem> shipmentItems) {
        this.shipmentItems = shipmentItems;
        return this;
    }

    public ShipmentOrder addShipmentItems(ShipmentItem shipmentItem) {
        this.shipmentItems.add(shipmentItem);
        shipmentItem.setOrder(this);
        return this;
    }

    public ShipmentOrder removeShipmentItems(ShipmentItem shipmentItem) {
        this.shipmentItems.remove(shipmentItem);
        shipmentItem.setOrder(null);
        return this;
    }

    public void setShipmentItems(Set<ShipmentItem> shipmentItems) {
        this.shipmentItems = shipmentItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShipmentOrder shipmentOrder = (ShipmentOrder) o;
        if (shipmentOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipmentOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipmentOrder{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", amount='" + getAmount() + "'" +
            "}";
    }
}
