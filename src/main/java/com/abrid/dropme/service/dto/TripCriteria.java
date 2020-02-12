package com.abrid.dropme.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.abrid.dropme.domain.enumeration.EMarchandise;
import com.abrid.dropme.domain.enumeration.ETripState;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.abrid.dropme.domain.Trip} entity. This class is used
 * in {@link com.abrid.dropme.web.rest.TripResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trips?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TripCriteria implements Serializable, Criteria {
    /**
     * Class for filtering EMarchandise
     */
    public static class EMarchandiseFilter extends Filter<EMarchandise> {

        public EMarchandiseFilter() {
        }

        public EMarchandiseFilter(EMarchandiseFilter filter) {
            super(filter);
        }

        @Override
        public EMarchandiseFilter copy() {
            return new EMarchandiseFilter(this);
        }

    }
    /**
     * Class for filtering ETripState
     */
    public static class ETripStateFilter extends Filter<ETripState> {

        public ETripStateFilter() {
        }

        public ETripStateFilter(ETripStateFilter filter) {
            super(filter);
        }

        @Override
        public ETripStateFilter copy() {
            return new ETripStateFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter isFull;

    private IntegerFilter width;

    private IntegerFilter height;

    private IntegerFilter length;

    private IntegerFilter weight;

    private EMarchandiseFilter marchandise;

    private ZonedDateTimeFilter etd;

    private StringFilter description;

    private ETripStateFilter state;

    private ZonedDateTimeFilter eta;

    private IntegerFilter distance;

    private LongFilter originId;

    private LongFilter destinationId;

    private LongFilter conversationId;

    private LongFilter truckId;

    private LongFilter clientAccountId;

    public TripCriteria() {
    }

    public TripCriteria(TripCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isFull = other.isFull == null ? null : other.isFull.copy();
        this.width = other.width == null ? null : other.width.copy();
        this.height = other.height == null ? null : other.height.copy();
        this.length = other.length == null ? null : other.length.copy();
        this.weight = other.weight == null ? null : other.weight.copy();
        this.marchandise = other.marchandise == null ? null : other.marchandise.copy();
        this.etd = other.etd == null ? null : other.etd.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.eta = other.eta == null ? null : other.eta.copy();
        this.distance = other.distance == null ? null : other.distance.copy();
        this.originId = other.originId == null ? null : other.originId.copy();
        this.destinationId = other.destinationId == null ? null : other.destinationId.copy();
        this.conversationId = other.conversationId == null ? null : other.conversationId.copy();
        this.truckId = other.truckId == null ? null : other.truckId.copy();
        this.clientAccountId = other.clientAccountId == null ? null : other.clientAccountId.copy();
    }

    @Override
    public TripCriteria copy() {
        return new TripCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getIsFull() {
        return isFull;
    }

    public void setIsFull(BooleanFilter isFull) {
        this.isFull = isFull;
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

    public IntegerFilter getWeight() {
        return weight;
    }

    public void setWeight(IntegerFilter weight) {
        this.weight = weight;
    }

    public EMarchandiseFilter getMarchandise() {
        return marchandise;
    }

    public void setMarchandise(EMarchandiseFilter marchandise) {
        this.marchandise = marchandise;
    }

    public ZonedDateTimeFilter getEtd() {
        return etd;
    }

    public void setEtd(ZonedDateTimeFilter etd) {
        this.etd = etd;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public ETripStateFilter getState() {
        return state;
    }

    public void setState(ETripStateFilter state) {
        this.state = state;
    }

    public ZonedDateTimeFilter getEta() {
        return eta;
    }

    public void setEta(ZonedDateTimeFilter eta) {
        this.eta = eta;
    }

    public IntegerFilter getDistance() {
        return distance;
    }

    public void setDistance(IntegerFilter distance) {
        this.distance = distance;
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

    public LongFilter getConversationId() {
        return conversationId;
    }

    public void setConversationId(LongFilter conversationId) {
        this.conversationId = conversationId;
    }

    public LongFilter getTruckId() {
        return truckId;
    }

    public void setTruckId(LongFilter truckId) {
        this.truckId = truckId;
    }

    public LongFilter getClientAccountId() {
        return clientAccountId;
    }

    public void setClientAccountId(LongFilter clientAccountId) {
        this.clientAccountId = clientAccountId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TripCriteria that = (TripCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(isFull, that.isFull) &&
            Objects.equals(width, that.width) &&
            Objects.equals(height, that.height) &&
            Objects.equals(length, that.length) &&
            Objects.equals(weight, that.weight) &&
            Objects.equals(marchandise, that.marchandise) &&
            Objects.equals(etd, that.etd) &&
            Objects.equals(description, that.description) &&
            Objects.equals(state, that.state) &&
            Objects.equals(eta, that.eta) &&
            Objects.equals(distance, that.distance) &&
            Objects.equals(originId, that.originId) &&
            Objects.equals(destinationId, that.destinationId) &&
            Objects.equals(conversationId, that.conversationId) &&
            Objects.equals(truckId, that.truckId) &&
            Objects.equals(clientAccountId, that.clientAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        isFull,
        width,
        height,
        length,
        weight,
        marchandise,
        etd,
        description,
        state,
        eta,
        distance,
        originId,
        destinationId,
        conversationId,
        truckId,
        clientAccountId
        );
    }

    @Override
    public String toString() {
        return "TripCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (isFull != null ? "isFull=" + isFull + ", " : "") +
                (width != null ? "width=" + width + ", " : "") +
                (height != null ? "height=" + height + ", " : "") +
                (length != null ? "length=" + length + ", " : "") +
                (weight != null ? "weight=" + weight + ", " : "") +
                (marchandise != null ? "marchandise=" + marchandise + ", " : "") +
                (etd != null ? "etd=" + etd + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (eta != null ? "eta=" + eta + ", " : "") +
                (distance != null ? "distance=" + distance + ", " : "") +
                (originId != null ? "originId=" + originId + ", " : "") +
                (destinationId != null ? "destinationId=" + destinationId + ", " : "") +
                (conversationId != null ? "conversationId=" + conversationId + ", " : "") +
                (truckId != null ? "truckId=" + truckId + ", " : "") +
                (clientAccountId != null ? "clientAccountId=" + clientAccountId + ", " : "") +
            "}";
    }

}
