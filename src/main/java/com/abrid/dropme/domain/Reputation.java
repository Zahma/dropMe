package com.abrid.dropme.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Reputation.
 */
@Entity
@Table(name = "reputation")
public class Reputation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "rate", nullable = false)
    private Integer rate;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JsonIgnoreProperties("reputations")
    private TransporterAccount transporterAccount;

    @ManyToOne
    @JsonIgnoreProperties("reputations")
    private ClientAccount clientAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRate() {
        return rate;
    }

    public Reputation rate(Integer rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public Reputation comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TransporterAccount getTransporterAccount() {
        return transporterAccount;
    }

    public Reputation transporterAccount(TransporterAccount transporterAccount) {
        this.transporterAccount = transporterAccount;
        return this;
    }

    public void setTransporterAccount(TransporterAccount transporterAccount) {
        this.transporterAccount = transporterAccount;
    }

    public ClientAccount getClientAccount() {
        return clientAccount;
    }

    public Reputation clientAccount(ClientAccount clientAccount) {
        this.clientAccount = clientAccount;
        return this;
    }

    public void setClientAccount(ClientAccount clientAccount) {
        this.clientAccount = clientAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reputation)) {
            return false;
        }
        return id != null && id.equals(((Reputation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Reputation{" +
            "id=" + getId() +
            ", rate=" + getRate() +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
