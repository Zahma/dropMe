package com.abrid.dropme.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ClientAccount.
 */
@Entity
@Table(name = "client_account")
public class ClientAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "referred_by")
    private String referredBy;

    @Column(name = "referal")
    private String referal;

    @Column(name = "activated")
    private Boolean activated;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "clientAccount")
    private Set<Reputation> reputations = new HashSet<>();

    @OneToMany(mappedBy = "clientAccount")
    private Set<Trip> trips = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public ClientAccount firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ClientAccount lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public ClientAccount phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public ClientAccount referredBy(String referredBy) {
        this.referredBy = referredBy;
        return this;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public String getReferal() {
        return referal;
    }

    public ClientAccount referal(String referal) {
        this.referal = referal;
        return this;
    }

    public void setReferal(String referal) {
        this.referal = referal;
    }

    public Boolean isActivated() {
        return activated;
    }

    public ClientAccount activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public User getUser() {
        return user;
    }

    public ClientAccount user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Reputation> getReputations() {
        return reputations;
    }

    public ClientAccount reputations(Set<Reputation> reputations) {
        this.reputations = reputations;
        return this;
    }

    public ClientAccount addReputation(Reputation reputation) {
        this.reputations.add(reputation);
        reputation.setClientAccount(this);
        return this;
    }

    public ClientAccount removeReputation(Reputation reputation) {
        this.reputations.remove(reputation);
        reputation.setClientAccount(null);
        return this;
    }

    public void setReputations(Set<Reputation> reputations) {
        this.reputations = reputations;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public ClientAccount trips(Set<Trip> trips) {
        this.trips = trips;
        return this;
    }

    public ClientAccount addTrip(Trip trip) {
        this.trips.add(trip);
        trip.setClientAccount(this);
        return this;
    }

    public ClientAccount removeTrip(Trip trip) {
        this.trips.remove(trip);
        trip.setClientAccount(null);
        return this;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientAccount)) {
            return false;
        }
        return id != null && id.equals(((ClientAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ClientAccount{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", referredBy='" + getReferredBy() + "'" +
            ", referal='" + getReferal() + "'" +
            ", activated='" + isActivated() + "'" +
            "}";
    }
}
