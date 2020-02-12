package com.abrid.dropme.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TransporterAccount.
 */
@Entity
@Table(name = "transporter_account")
public class TransporterAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Lob
    @Column(name = "patent")
    private byte[] patent;

    @Column(name = "patent_content_type")
    private String patentContentType;

    @Column(name = "manager_f_name")
    private String managerFName;

    @Column(name = "manager_l_name")
    private String managerLName;

    @Column(name = "balance")
    private Integer balance;

    @Lob
    @Column(name = "insurance")
    private byte[] insurance;

    @Column(name = "insurance_content_type")
    private String insuranceContentType;

    @Column(name = "referal")
    private String referal;

    @Column(name = "refered_by")
    private String referedBy;

    @Column(name = "activated")
    private Boolean activated;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "transporterAccount")
    private Set<Truck> trucks = new HashSet<>();

    @OneToMany(mappedBy = "transporterAccount")
    private Set<Reputation> reputations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TransporterAccount name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public TransporterAccount phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getPatent() {
        return patent;
    }

    public TransporterAccount patent(byte[] patent) {
        this.patent = patent;
        return this;
    }

    public void setPatent(byte[] patent) {
        this.patent = patent;
    }

    public String getPatentContentType() {
        return patentContentType;
    }

    public TransporterAccount patentContentType(String patentContentType) {
        this.patentContentType = patentContentType;
        return this;
    }

    public void setPatentContentType(String patentContentType) {
        this.patentContentType = patentContentType;
    }

    public String getManagerFName() {
        return managerFName;
    }

    public TransporterAccount managerFName(String managerFName) {
        this.managerFName = managerFName;
        return this;
    }

    public void setManagerFName(String managerFName) {
        this.managerFName = managerFName;
    }

    public String getManagerLName() {
        return managerLName;
    }

    public TransporterAccount managerLName(String managerLName) {
        this.managerLName = managerLName;
        return this;
    }

    public void setManagerLName(String managerLName) {
        this.managerLName = managerLName;
    }

    public Integer getBalance() {
        return balance;
    }

    public TransporterAccount balance(Integer balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public byte[] getInsurance() {
        return insurance;
    }

    public TransporterAccount insurance(byte[] insurance) {
        this.insurance = insurance;
        return this;
    }

    public void setInsurance(byte[] insurance) {
        this.insurance = insurance;
    }

    public String getInsuranceContentType() {
        return insuranceContentType;
    }

    public TransporterAccount insuranceContentType(String insuranceContentType) {
        this.insuranceContentType = insuranceContentType;
        return this;
    }

    public void setInsuranceContentType(String insuranceContentType) {
        this.insuranceContentType = insuranceContentType;
    }

    public String getReferal() {
        return referal;
    }

    public TransporterAccount referal(String referal) {
        this.referal = referal;
        return this;
    }

    public void setReferal(String referal) {
        this.referal = referal;
    }

    public String getReferedBy() {
        return referedBy;
    }

    public TransporterAccount referedBy(String referedBy) {
        this.referedBy = referedBy;
        return this;
    }

    public void setReferedBy(String referedBy) {
        this.referedBy = referedBy;
    }

    public Boolean isActivated() {
        return activated;
    }

    public TransporterAccount activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public User getUser() {
        return user;
    }

    public TransporterAccount user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Truck> getTrucks() {
        return trucks;
    }

    public TransporterAccount trucks(Set<Truck> trucks) {
        this.trucks = trucks;
        return this;
    }

    public TransporterAccount addTruck(Truck truck) {
        this.trucks.add(truck);
        truck.setTransporterAccount(this);
        return this;
    }

    public TransporterAccount removeTruck(Truck truck) {
        this.trucks.remove(truck);
        truck.setTransporterAccount(null);
        return this;
    }

    public void setTrucks(Set<Truck> trucks) {
        this.trucks = trucks;
    }

    public Set<Reputation> getReputations() {
        return reputations;
    }

    public TransporterAccount reputations(Set<Reputation> reputations) {
        this.reputations = reputations;
        return this;
    }

    public TransporterAccount addReputation(Reputation reputation) {
        this.reputations.add(reputation);
        reputation.setTransporterAccount(this);
        return this;
    }

    public TransporterAccount removeReputation(Reputation reputation) {
        this.reputations.remove(reputation);
        reputation.setTransporterAccount(null);
        return this;
    }

    public void setReputations(Set<Reputation> reputations) {
        this.reputations = reputations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransporterAccount)) {
            return false;
        }
        return id != null && id.equals(((TransporterAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransporterAccount{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", patent='" + getPatent() + "'" +
            ", patentContentType='" + getPatentContentType() + "'" +
            ", managerFName='" + getManagerFName() + "'" +
            ", managerLName='" + getManagerLName() + "'" +
            ", balance=" + getBalance() +
            ", insurance='" + getInsurance() + "'" +
            ", insuranceContentType='" + getInsuranceContentType() + "'" +
            ", referal='" + getReferal() + "'" +
            ", referedBy='" + getReferedBy() + "'" +
            ", activated='" + isActivated() + "'" +
            "}";
    }
}
