package com.abrid.dropme.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Destination.
 */
@Entity
@Table(name = "destination")
public class Destination implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "destination")
    @JsonIgnore
    private Location location;

    @OneToOne(mappedBy = "destination")
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

    public Destination location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Trip getTrip() {
        return trip;
    }

    public Destination trip(Trip trip) {
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
        if (!(o instanceof Destination)) {
            return false;
        }
        return id != null && id.equals(((Destination) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Destination{" +
            "id=" + getId() +
            "}";
    }
}
