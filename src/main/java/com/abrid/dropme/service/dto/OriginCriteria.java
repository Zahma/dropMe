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
 * Criteria class for the {@link com.abrid.dropme.domain.Origin} entity. This class is used
 * in {@link com.abrid.dropme.web.rest.OriginResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /origins?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OriginCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter locationId;

    private LongFilter tripId;

    public OriginCriteria() {
    }

    public OriginCriteria(OriginCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.tripId = other.tripId == null ? null : other.tripId.copy();
    }

    @Override
    public OriginCriteria copy() {
        return new OriginCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getTripId() {
        return tripId;
    }

    public void setTripId(LongFilter tripId) {
        this.tripId = tripId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OriginCriteria that = (OriginCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(tripId, that.tripId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        locationId,
        tripId
        );
    }

    @Override
    public String toString() {
        return "OriginCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
                (tripId != null ? "tripId=" + tripId + ", " : "") +
            "}";
    }

}
