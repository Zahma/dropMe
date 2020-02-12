package com.abrid.dropme.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.abrid.dropme.domain.enumeration.ETruckType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.abrid.dropme.domain.Truck} entity. This class is used
 * in {@link com.abrid.dropme.web.rest.TruckResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trucks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TruckCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ETruckType
     */
    public static class ETruckTypeFilter extends Filter<ETruckType> {

        public ETruckTypeFilter() {
        }

        public ETruckTypeFilter(ETruckTypeFilter filter) {
            super(filter);
        }

        @Override
        public ETruckTypeFilter copy() {
            return new ETruckTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter plateNumber;

    private StringFilter conteneurPlateNumber;

    private ETruckTypeFilter type;

    private IntegerFilter width;

    private IntegerFilter height;

    private IntegerFilter length;

    private IntegerFilter maxWeight;

    private LongFilter driverId;

    private LongFilter tripId;

    private LongFilter conversationId;

    private LongFilter transporterAccountId;

    public TruckCriteria() {
    }

    public TruckCriteria(TruckCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.plateNumber = other.plateNumber == null ? null : other.plateNumber.copy();
        this.conteneurPlateNumber = other.conteneurPlateNumber == null ? null : other.conteneurPlateNumber.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.width = other.width == null ? null : other.width.copy();
        this.height = other.height == null ? null : other.height.copy();
        this.length = other.length == null ? null : other.length.copy();
        this.maxWeight = other.maxWeight == null ? null : other.maxWeight.copy();
        this.driverId = other.driverId == null ? null : other.driverId.copy();
        this.tripId = other.tripId == null ? null : other.tripId.copy();
        this.conversationId = other.conversationId == null ? null : other.conversationId.copy();
        this.transporterAccountId = other.transporterAccountId == null ? null : other.transporterAccountId.copy();
    }

    @Override
    public TruckCriteria copy() {
        return new TruckCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(StringFilter plateNumber) {
        this.plateNumber = plateNumber;
    }

    public StringFilter getConteneurPlateNumber() {
        return conteneurPlateNumber;
    }

    public void setConteneurPlateNumber(StringFilter conteneurPlateNumber) {
        this.conteneurPlateNumber = conteneurPlateNumber;
    }

    public ETruckTypeFilter getType() {
        return type;
    }

    public void setType(ETruckTypeFilter type) {
        this.type = type;
    }

    public IntegerFilter getWidth() {
        return width;
    }

    public void setWidth(IntegerFilter width) {
        this.width = width;
    }

    public IntegerFilter getHeight() {
        return height;
    }

    public void setHeight(IntegerFilter height) {
        this.height = height;
    }

    public IntegerFilter getLength() {
        return length;
    }

    public void setLength(IntegerFilter length) {
        this.length = length;
    }

    public IntegerFilter getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(IntegerFilter maxWeight) {
        this.maxWeight = maxWeight;
    }

    public LongFilter getDriverId() {
        return driverId;
    }

    public void setDriverId(LongFilter driverId) {
        this.driverId = driverId;
    }

    public LongFilter getTripId() {
        return tripId;
    }

    public void setTripId(LongFilter tripId) {
        this.tripId = tripId;
    }

    public LongFilter getConversationId() {
        return conversationId;
    }

    public void setConversationId(LongFilter conversationId) {
        this.conversationId = conversationId;
    }

    public LongFilter getTransporterAccountId() {
        return transporterAccountId;
    }

    public void setTransporterAccountId(LongFilter transporterAccountId) {
        this.transporterAccountId = transporterAccountId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TruckCriteria that = (TruckCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(plateNumber, that.plateNumber) &&
            Objects.equals(conteneurPlateNumber, that.conteneurPlateNumber) &&
            Objects.equals(type, that.type) &&
            Objects.equals(width, that.width) &&
            Objects.equals(height, that.height) &&
            Objects.equals(length, that.length) &&
            Objects.equals(maxWeight, that.maxWeight) &&
            Objects.equals(driverId, that.driverId) &&
            Objects.equals(tripId, that.tripId) &&
            Objects.equals(conversationId, that.conversationId) &&
            Objects.equals(transporterAccountId, that.transporterAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        plateNumber,
        conteneurPlateNumber,
        type,
        width,
        height,
        length,
        maxWeight,
        driverId,
        tripId,
        conversationId,
        transporterAccountId
        );
    }

    @Override
    public String toString() {
        return "TruckCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (plateNumber != null ? "plateNumber=" + plateNumber + ", " : "") +
                (conteneurPlateNumber != null ? "conteneurPlateNumber=" + conteneurPlateNumber + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (width != null ? "width=" + width + ", " : "") +
                (height != null ? "height=" + height + ", " : "") +
                (length != null ? "length=" + length + ", " : "") +
                (maxWeight != null ? "maxWeight=" + maxWeight + ", " : "") +
                (driverId != null ? "driverId=" + driverId + ", " : "") +
                (tripId != null ? "tripId=" + tripId + ", " : "") +
                (conversationId != null ? "conversationId=" + conversationId + ", " : "") +
                (transporterAccountId != null ? "transporterAccountId=" + transporterAccountId + ", " : "") +
            "}";
    }

}
