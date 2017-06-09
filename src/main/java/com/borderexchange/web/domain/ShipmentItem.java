package com.borderexchange.web.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.borderexchange.web.domain.enumeration.ShipmentItemStatus;

/**
 * A ShipmentItem.
 */
@Entity
@Table(name = "shipment_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShipmentItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "price")
    private Long price;

    @Column(name = "free_storage_ending_date")
    private ZonedDateTime freeStorageEndingDate;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ShipmentItemStatus status;

    @OneToOne
    @JoinColumn(unique = true)
    private ShipmentItemProperties itemProperties;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User user;

    @ManyToOne
    private ShipmentItemCategory category;

    @ManyToOne
    private ShipmentOrder order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ShipmentItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ShipmentItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public ShipmentItem longDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Long getPrice() {
        return price;
    }

    public ShipmentItem price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ZonedDateTime getFreeStorageEndingDate() {
        return freeStorageEndingDate;
    }

    public ShipmentItem freeStorageEndingDate(ZonedDateTime freeStorageEndingDate) {
        this.freeStorageEndingDate = freeStorageEndingDate;
        return this;
    }

    public void setFreeStorageEndingDate(ZonedDateTime freeStorageEndingDate) {
        this.freeStorageEndingDate = freeStorageEndingDate;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public ShipmentItem createdOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public ShipmentItemStatus getStatus() {
        return status;
    }

    public ShipmentItem status(ShipmentItemStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ShipmentItemStatus status) {
        this.status = status;
    }

    public ShipmentItemProperties getItemProperties() {
        return itemProperties;
    }

    public ShipmentItem itemProperties(ShipmentItemProperties shipmentItemProperties) {
        this.itemProperties = shipmentItemProperties;
        return this;
    }

    public void setItemProperties(ShipmentItemProperties shipmentItemProperties) {
        this.itemProperties = shipmentItemProperties;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public ShipmentItem createdBy(User user) {
        this.createdBy = user;
        return this;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public User getUser() {
        return user;
    }

    public ShipmentItem user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ShipmentItemCategory getCategory() {
        return category;
    }

    public ShipmentItem category(ShipmentItemCategory shipmentItemCategory) {
        this.category = shipmentItemCategory;
        return this;
    }

    public void setCategory(ShipmentItemCategory shipmentItemCategory) {
        this.category = shipmentItemCategory;
    }

    public ShipmentOrder getOrder() {
        return order;
    }

    public ShipmentItem order(ShipmentOrder shipmentOrder) {
        this.order = shipmentOrder;
        return this;
    }

    public void setOrder(ShipmentOrder shipmentOrder) {
        this.order = shipmentOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShipmentItem shipmentItem = (ShipmentItem) o;
        if (shipmentItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipmentItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipmentItem{" +
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
