package com.abrid.dropme.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.abrid.dropme.domain.ClientAccount} entity.
 */
public class ClientAccountDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String phone;

    private String referredBy;

    private String referal;

    private Boolean activated;


    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public String getReferal() {
        return referal;
    }

    public void setReferal(String referal) {
        this.referal = referal;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientAccountDTO clientAccountDTO = (ClientAccountDTO) o;
        if (clientAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientAccountDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", referredBy='" + getReferredBy() + "'" +
            ", referal='" + getReferal() + "'" +
            ", activated='" + isActivated() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
