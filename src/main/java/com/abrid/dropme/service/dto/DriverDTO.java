package com.abrid.dropme.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.abrid.dropme.domain.Driver} entity.
 */
public class DriverDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String currentCoordinate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCurrentCoordinate() {
        return currentCoordinate;
    }

    public void setCurrentCoordinate(String currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DriverDTO driverDTO = (DriverDTO) o;
        if (driverDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), driverDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DriverDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", currentCoordinate='" + getCurrentCoordinate() + "'" +
            "}";
    }
}
