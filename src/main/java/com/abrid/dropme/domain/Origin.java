package com.abrid.dropme.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Origin.
 */
@Entity
@Table(name = "origin")
public class Origin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "origin")
    @JsonIgnore
    private Location location;

    @OneToOne(mappedBy = "origin")
    @JsonIgnore
    private Trip trip;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public Origin location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Trip getTrip() {
        return trip;
    }

    public Origin trip(Trip trip) {
        this.trip = trip;
        return this;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Origin)) {
            return false;
        }
        return id != null && id.equals(((Origin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Origin{" +
            "id=" + getId() +
            "}";
    }
}
