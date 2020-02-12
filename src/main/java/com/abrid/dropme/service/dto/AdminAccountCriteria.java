package com.abrid.dropme.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.abrid.dropme.domain.enumeration.ERole;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.abrid.dropme.domain.AdminAccount} entity. This class is used
 * in {@link com.abrid.dropme.web.rest.AdminAccountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /admin-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdminAccountCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ERole
     */
    public static class ERoleFilter extends Filter<ERole> {

        public ERoleFilter() {
        }

        public ERoleFilter(ERoleFilter filter) {
            super(filter);
        }

        @Override
        public ERoleFilter copy() {
            return new ERoleFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ERoleFilter role;

    private LongFilter userId;

    public AdminAccountCriteria() {
    }

    public AdminAccountCriteria(AdminAccountCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.role = other.role == null ? null : other.role.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public AdminAccountCriteria copy() {
        return new AdminAccountCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ERoleFilter getRole() {
        return role;
    }

    public void setRole(ERoleFilter role) {
        this.role = role;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdminAccountCriteria that = (AdminAccountCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(role, that.role) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        role,
        userId
        );
    }

    @Override
    public String toString() {
        return "AdminAccountCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (role != null ? "role=" + role + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
