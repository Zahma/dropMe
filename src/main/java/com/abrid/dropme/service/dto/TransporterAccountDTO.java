package com.abrid.dropme.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.abrid.dropme.domain.TransporterAccount} entity.
 */
public class TransporterAccountDTO implements Serializable {

    private Long id;

    private String name;

    private String phone;

    @Lob
    private byte[] patent;

    private String patentContentType;
    private String managerFName;

    private String managerLName;

    private Integer balance;

    @Lob
    private byte[] insurance;

    private String insuranceContentType;
    private String referal;

    private String referedBy;

    private Boolean activated;


    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getPatent() {
        return patent;
    }

    public void setPatent(byte[] patent) {
        this.patent = patent;
    }

    public String getPatentContentType() {
        return patentContentType;
    }

    public void setPatentContentType(String patentContentType) {
        this.patentContentType = patentContentType;
    }

    public String getManagerFName() {
        return managerFName;
    }

    public void setManagerFName(String managerFName) {
        this.managerFName = managerFName;
    }

    public String getManagerLName() {
        return managerLName;
    }

    public void setManagerLName(String managerLName) {
        this.managerLName = managerLName;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public byte[] getInsurance() {
        return insurance;
    }

    public void setInsurance(byte[] insurance) {
        this.insurance = insurance;
    }

    public String getInsuranceContentType() {
        return insuranceContentType;
    }

    public void setInsuranceContentType(String insuranceContentType) {
        this.insuranceContentType = insuranceContentType;
    }

    public String getReferal() {
        return referal;
    }

    public void setReferal(String referal) {
        this.referal = referal;
    }

    public String getReferedBy() {
        return referedBy;
    }

    public void setReferedBy(String referedBy) {
        this.referedBy = referedBy;
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

        TransporterAccountDTO transporterAccountDTO = (TransporterAccountDTO) o;
        if (transporterAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transporterAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransporterAccountDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", patent='" + getPatent() + "'" +
            ", managerFName='" + getManagerFName() + "'" +
            ", managerLName='" + getManagerLName() + "'" +
            ", balance=" + getBalance() +
            ", insurance='" + getInsurance() + "'" +
            ", referal='" + getReferal() + "'" +
            ", referedBy='" + getReferedBy() + "'" +
            ", activated='" + isActivated() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
