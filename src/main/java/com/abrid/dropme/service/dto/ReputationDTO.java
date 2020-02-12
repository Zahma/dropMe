package com.abrid.dropme.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.abrid.dropme.domain.Reputation} entity.
 */
public class ReputationDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer rate;

    private String comment;


    private Long transporterAccountId;

    private Long clientAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getTransporterAccountId() {
        return transporterAccountId;
    }

    public void setTransporterAccountId(Long transporterAccountId) {
        this.transporterAccountId = transporterAccountId;
    }

    public Long getClientAccountId() {
        return clientAccountId;
    }

    public void setClientAccountId(Long clientAccountId) {
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

        ReputationDTO reputationDTO = (ReputationDTO) o;
        if (reputationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reputationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReputationDTO{" +
            "id=" + getId() +
            ", rate=" + getRate() +
            ", comment='" + getComment() + "'" +
            ", transporterAccountId=" + getTransporterAccountId() +
            ", clientAccountId=" + getClientAccountId() +
            "}";
    }
}
