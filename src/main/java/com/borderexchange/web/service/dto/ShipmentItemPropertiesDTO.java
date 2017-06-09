package com.borderexchange.web.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ShipmentItemProperties entity.
 */
public class ShipmentItemPropertiesDTO implements Serializable {

    private Long id;

    private Double height;

    private Double length;

    private Double width;

    private Double weight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShipmentItemPropertiesDTO shipmentItemPropertiesDTO = (ShipmentItemPropertiesDTO) o;
        if(shipmentItemPropertiesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipmentItemPropertiesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipmentItemPropertiesDTO{" +
            "id=" + getId() +
            ", height='" + getHeight() + "'" +
            ", length='" + getLength() + "'" +
            ", width='" + getWidth() + "'" +
            ", weight='" + getWeight() + "'" +
            "}";
    }
}
