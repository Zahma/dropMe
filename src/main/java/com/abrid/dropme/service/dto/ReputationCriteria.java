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
 * Criteria class for the {@link com.abrid.dropme.domain.Reputation} entity. This class is used
 * in {@link com.abrid.dropme.web.rest.ReputationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /reputations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReputationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter rate;

    private StringFilter comment;

    private LongFilter transporterAccountId;

    private LongFilter clientAccountId;

    public ReputationCriteria() {
    }

    public ReputationCriteria(ReputationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rate = other.rate == null ? null : other.rate.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.transporterAccountId = other.transporterAccountId == null ? null : other.transporterAccountId.copy();
        this.clientAccountId = other.clientAccountId == null ? null : other.clientAccountId.copy();
    }

    @Override
    public ReputationCriteria copy() {
        return new ReputationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getRate() {
        return rate;
    }

    public void setRate(IntegerFilter rate) {
        this.rate = rate;
    }

    public StringFilter getComment() {
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    public LongFilter getTransporterAccountId() {
        return transporterAccountId;
    }

    public void setTransporterAccountId(LongFilter transporterAccountId) {
        this.transporterAccountId = transporterAccountId;
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
        final ReputationCriteria that = (ReputationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(rate, that.rate) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(transporterAccountId, that.transporterAccountId) &&
            Objects.equals(clientAccountId, that.clientAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        rate,
        comment,
        transporterAccountId,
        clientAccountId
        );
    }

    @Override
    public String toString() {
        return "ReputationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (rate != null ? "rate=" + rate + ", " : "") +
                (comment != null ? "comment=" + comment + ", " : "") +
                (transporterAccountId != null ? "transporterAccountId=" + transporterAccountId + ", " : "") +
                (clientAccountId != null ? "clientAccountId=" + clientAccountId + ", " : "") +
            "}";
    }

}
