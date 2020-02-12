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
 * Criteria class for the {@link com.abrid.dropme.domain.Location} entity. This class is used
 * in {@link com.abrid.dropme.web.rest.LocationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /locations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LocationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter streetAddress;

    private StringFilter postalCode;

    private StringFilter city;

    private StringFilter stateProvince;

    private LongFilter originId;

    private LongFilter destinationId;

    private LongFilter countryId;

    public LocationCriteria() {
    }

    public LocationCriteria(LocationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.streetAddress = other.streetAddress == null ? null : other.streetAddress.copy();
        this.postalCode = other.postalCode == null ? null : other.postalCode.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.stateProvince = other.stateProvince == null ? null : other.stateProvince.copy();
        this.originId = other.originId == null ? null : other.originId.copy();
        this.destinationId = other.destinationId == null ? null : other.destinationId.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
    }

    @Override
    public LocationCriteria copy() {
        return new LocationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(StringFilter streetAddress) {
        this.streetAddress = streetAddress;
    }

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(StringFilter stateProvince) {
        this.stateProvince = stateProvince;
    }

    public LongFilter getOriginId() {
        return originId;
    }

    public void setOriginId(LongFilter originId) {
        this.originId = originId;
    }

    public LongFilter getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(LongFilter destinationId) {
        this.destinationId = destinationId;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LocationCriteria that = (LocationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(streetAddress, that.streetAddress) &&
            Objects.equals(postalCode, that.postalCode) &&
            Objects.equals(city, that.city) &&
            Objects.equals(stateProvince, that.stateProvince) &&
            Objects.equals(originId, that.originId) &&
            Objects.equals(destinationId, that.destinationId) &&
            Objects.equals(countryId, that.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        streetAddress,
        postalCode,
        city,
        stateProvince,
        originId,
        destinationId,
        countryId
        );
    }

    @Override
    public String toString() {
        return "LocationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (streetAddress != null ? "streetAddress=" + streetAddress + ", " : "") +
                (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (stateProvince != null ? "stateProvince=" + stateProvince + ", " : "") +
                (originId != null ? "originId=" + originId + ", " : "") +
                (destinationId != null ? "destinationId=" + destinationId + ", " : "") +
                (countryId != null ? "countryId=" + countryId + ", " : "") +
            "}";
    }

}
