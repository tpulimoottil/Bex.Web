package com.borderexchange.web.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A State.
 */
@Entity
@Table(name = "state")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "iso_2")
    private String iso2;

    @Column(name = "iso_3")
    private String iso3;

    @Column(name = "iso_number")
    private Integer isoNumber;

    @ManyToOne
    private Country country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public State stateName(String stateName) {
        this.stateName = stateName;
        return this;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getIso2() {
        return iso2;
    }

    public State iso2(String iso2) {
        this.iso2 = iso2;
        return this;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public State iso3(String iso3) {
        this.iso3 = iso3;
        return this;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public Integer getIsoNumber() {
        return isoNumber;
    }

    public State isoNumber(Integer isoNumber) {
        this.isoNumber = isoNumber;
        return this;
    }

    public void setIsoNumber(Integer isoNumber) {
        this.isoNumber = isoNumber;
    }

    public Country getCountry() {
        return country;
    }

    public State country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        State state = (State) o;
        if (state.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), state.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "State{" +
            "id=" + getId() +
            ", stateName='" + getStateName() + "'" +
            ", iso2='" + getIso2() + "'" +
            ", iso3='" + getIso3() + "'" +
            ", isoNumber='" + getIsoNumber() + "'" +
            "}";
    }
}
