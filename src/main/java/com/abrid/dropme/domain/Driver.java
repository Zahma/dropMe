package com.abrid.dropme.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "current_coordinate")
    private String currentCoordinate;

    @OneToOne(mappedBy = "driver")
    @JsonIgnore
    private Truck truck;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Driver firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Driver lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCurrentCoordinate() {
        return currentCoordinate;
    }

    public Driver currentCoordinate(String currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
        return this;
    }

    public void setCurrentCoordinate(String currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }

    public Truck getTruck() {
        return truck;
    }

    public Driver truck(Truck truck) {
        this.truck = truck;
        return this;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Driver)) {
            return false;
        }
        return id != null && id.equals(((Driver) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Driver{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", currentCoordinate='" + getCurrentCoordinate() + "'" +
            "}";
    }
}
