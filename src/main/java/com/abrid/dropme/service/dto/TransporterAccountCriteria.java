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
 * Criteria class for the {@link com.abrid.dropme.domain.TransporterAccount} entity. This class is used
 * in {@link com.abrid.dropme.web.rest.TransporterAccountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transporter-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransporterAccountCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter phone;

    private StringFilter managerFName;

    private StringFilter managerLName;

    private IntegerFilter balance;

    private StringFilter referal;

    private StringFilter referedBy;

    private BooleanFilter activated;

    private LongFilter userId;

    private LongFilter truckId;

    private LongFilter reputationId;

    public TransporterAccountCriteria() {
    }

    public TransporterAccountCriteria(TransporterAccountCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.managerFName = other.managerFName == null ? null : other.managerFName.copy();
        this.managerLName = other.managerLName == null ? null : other.managerLName.copy();
        this.balance = other.balance == null ? null : other.balance.copy();
        this.referal = other.referal == null ? null : other.referal.copy();
        this.referedBy = other.referedBy == null ? null : other.referedBy.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.truckId = other.truckId == null ? null : other.truckId.copy();
        this.reputationId = other.reputationId == null ? null : other.reputationId.copy();
    }

    @Override
    public TransporterAccountCriteria copy() {
        return new TransporterAccountCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getManagerFName() {
        return managerFName;
    }

    public void setManagerFName(StringFilter managerFName) {
        this.managerFName = managerFName;
    }

    public StringFilter getManagerLName() {
        return managerLName;
    }

    public void setManagerLName(StringFilter managerLName) {
        this.managerLName = managerLName;
    }

    public IntegerFilter getBalance() {
        return balance;
    }

    public void setBalance(IntegerFilter balance) {
        this.balance = balance;
    }

    public StringFilter getReferal() {
        return referal;
    }

    public void setReferal(StringFilter referal) {
        this.referal = referal;
    }

    public StringFilter getReferedBy() {
        return referedBy;
    }

    public void setReferedBy(StringFilter referedBy) {
        this.referedBy = referedBy;
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

    public LongFilter getTruckId() {
        return truckId;
    }

    public void setTruckId(LongFilter truckId) {
        this.truckId = truckId;
    }

    public LongFilter getReputationId() {
        return reputationId;
    }

    public void setReputationId(LongFilter reputationId) {
        this.reputationId = reputationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TransporterAccountCriteria that = (TransporterAccountCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(managerFName, that.managerFName) &&
            Objects.equals(managerLName, that.managerLName) &&
            Objects.equals(balance, that.balance) &&
            Objects.equals(referal, that.referal) &&
            Objects.equals(referedBy, that.referedBy) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(truckId, that.truckId) &&
            Objects.equals(reputationId, that.reputationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        phone,
        managerFName,
        managerLName,
        balance,
        referal,
        referedBy,
        activated,
        userId,
        truckId,
        reputationId
        );
    }

    @Override
    public String toString() {
        return "TransporterAccountCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (managerFName != null ? "managerFName=" + managerFName + ", " : "") +
                (managerLName != null ? "managerLName=" + managerLName + ", " : "") +
                (balance != null ? "balance=" + balance + ", " : "") +
                (referal != null ? "referal=" + referal + ", " : "") +
                (referedBy != null ? "referedBy=" + referedBy + ", " : "") +
                (activated != null ? "activated=" + activated + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (truckId != null ? "truckId=" + truckId + ", " : "") +
                (reputationId != null ? "reputationId=" + reputationId + ", " : "") +
            "}";
    }

}
