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
 * Criteria class for the {@link com.abrid.dropme.domain.Conversation} entity. This class is used
 * in {@link com.abrid.dropme.web.rest.ConversationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /conversations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConversationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter chatId;

    private LongFilter tripId;

    private LongFilter truckId;

    public ConversationCriteria() {
    }

    public ConversationCriteria(ConversationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.chatId = other.chatId == null ? null : other.chatId.copy();
        this.tripId = other.tripId == null ? null : other.tripId.copy();
        this.truckId = other.truckId == null ? null : other.truckId.copy();
    }

    @Override
    public ConversationCriteria copy() {
        return new ConversationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getChatId() {
        return chatId;
    }

    public void setChatId(LongFilter chatId) {
        this.chatId = chatId;
    }

    public LongFilter getTripId() {
        return tripId;
    }

    public void setTripId(LongFilter tripId) {
        this.tripId = tripId;
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
        final ConversationCriteria that = (ConversationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(chatId, that.chatId) &&
            Objects.equals(tripId, that.tripId) &&
            Objects.equals(truckId, that.truckId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        chatId,
        tripId,
        truckId
        );
    }

    @Override
    public String toString() {
        return "ConversationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (chatId != null ? "chatId=" + chatId + ", " : "") +
                (tripId != null ? "tripId=" + tripId + ", " : "") +
                (truckId != null ? "truckId=" + truckId + ", " : "") +
            "}";
    }

}
