package com.abrid.dropme.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.abrid.dropme.domain.enumeration.ERole;

/**
 * A DTO for the {@link com.abrid.dropme.domain.AdminAccount} entity.
 */
public class AdminAccountDTO implements Serializable {

    private Long id;

    private ERole role;


    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
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

        AdminAccountDTO adminAccountDTO = (AdminAccountDTO) o;
        if (adminAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adminAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdminAccountDTO{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
