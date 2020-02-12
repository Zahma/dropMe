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
 * Criteria class for the {@link com.abrid.dropme.domain.ClientAccount} entity. This class is used
 * in {@link com.abrid.dropme.web.rest.ClientAccountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /client-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClientAccountCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter phone;

    private StringFilter referredBy;

    private StringFilter referal;

    private BooleanFilter activated;

    private LongFilter userId;

    private LongFilter reputationId;

    private LongFilter tripId;

    public ClientAccountCriteria() {
    }

    public ClientAccountCriteria(ClientAccountCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.referredBy = other.referredBy == null ? null : other.referredBy.copy();
        this.referal = other.referal == null ? null : other.referal.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.reputationId = other.reputationId == null ? null : other.reputationId.copy();
        this.tripId = other.tripId == null ? null : other.tripId.copy();
    }

    @Override
    public ClientAccountCriteria copy() {
        return new ClientAccountCriteria(this);
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

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(StringFilter referredBy) {
        this.referredBy = referredBy;
    }

    public StringFilter getReferal() {
        return referal;
    }

    public void setReferal(StringFilter referal) {
        this.referal = referal;
    }

    public BooleanFilter getActivated() {
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
        this.activated = activated;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getReputationId() {
        return reputationId;
    }

    public void setReputationId(LongFilter reputationId) {
        this.reputationId = reputationId;
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
        final ClientAccountCriteria that = (ClientAccountCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(referredBy, that.referredBy) &&
            Objects.equals(referal, that.referal) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(reputationId, that.reputationId) &&
            Objects.equals(tripId, that.tripId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        firstName,
        lastName,
        phone,
        referredBy,
        referal,
        activated,
        userId,
        reputationId,
        tripId
        );
    }

    @Override
    public String toString() {
        return "ClientAccountCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (referredBy != null ? "referredBy=" + referredBy + ", " : "") +
                (referal != null ? "referal=" + referal + ", " : "") +
                (activated != null ? "activated=" + activated + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (reputationId != null ? "reputationId=" + reputationId + ", " : "") +
                (tripId != null ? "tripId=" + tripId + ", " : "") +
            "}";
    }

}
