package com.abrid.dropme.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.abrid.dropme.domain.Driver} entity. This class is used
 * in {@link com.abrid.dropme.web.rest.DriverResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /drivers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DriverCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter currentCoordinate;

    private LongFilter truckId;

    public DriverCriteria() {
    }

    public DriverCriteria(DriverCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.currentCoordinate = other.currentCoordinate == null ? null : other.currentCoordinate.copy();
        this.truckId = other.truckId == null ? null : other.truckId.copy();
    }

    @Override
    public DriverCriteria copy() {
        return new DriverCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getCurrentCoordinate() {
        return currentCoordinate;
    }

    public void setCurrentCoordinate(StringFilter currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }

    public LongFilter getTruckId() {
        return truckId;
    }

    public void setTruckId(LongFilter truckId) {
        this.truckId = truckId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DriverCriteria that = (DriverCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(currentCoordinate, that.currentCoordinate) &&
            Objects.equals(truckId, that.truckId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        firstName,
        lastName,
        currentCoordinate,
        truckId
        );
    }

    @Override
    public String toString() {
        return "DriverCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (currentCoordinate != null ? "currentCoordinate=" + currentCoordinate + ", " : "") +
                (truckId != null ? "truckId=" + truckId + ", " : "") +
            "}";
    }

}
