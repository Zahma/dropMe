package com.abrid.dropme.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.abrid.dropme.domain.enumeration.EMarchandise;

import com.abrid.dropme.domain.enumeration.ETripState;

/**
 * A Trip.
 */
@Entity
@Table(name = "trip")
public class Trip implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "is_full", nullable = false)
    private Boolean isFull;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "length")
    private Integer length;

    @Column(name = "weight")
    private Integer weight;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marchandise", nullable = false)
    private EMarchandise marchandise;

    @NotNull
    @Column(name = "etd", nullable = false)
    private ZonedDateTime etd;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private ETripState state;

    @Column(name = "eta")
    private ZonedDateTime eta;

    @NotNull
    @Column(name = "distance", nullable = false)
    private Integer distance;

    @OneToOne
    @JoinColumn(unique = true)
    private Origin origin;

    @OneToOne
    @JoinColumn(unique = true)
    private Destination destination;

    @OneToMany(mappedBy = "trip")
    private Set<Conversation> conversations = new HashSet<>();

    @OneToOne(mappedBy = "trip")
    @JsonIgnore
    private Truck truck;

    @ManyToOne
    @JsonIgnoreProperties("trips")
    private ClientAccount clientAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsFull() {
        return isFull;
    }

    public Trip isFull(Boolean isFull) {
        this.isFull = isFull;
        return this;
    }

    public void setIsFull(Boolean isFull) {
        this.isFull = isFull;
    }

    public Integer getWidth() {
        return width;
    }

    public Trip width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public Trip height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLength() {
        return length;
    }

    public Trip length(Integer length) {
        this.length = length;
        return this;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWeight() {
        return weight;
    }

    public Trip weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public EMarchandise getMarchandise() {
        return marchandise;
    }

    public Trip marchandise(EMarchandise marchandise) {
        this.marchandise = marchandise;
        return this;
    }

    public void setMarchandise(EMarchandise marchandise) {
        this.marchandise = marchandise;
    }

    public ZonedDateTime getEtd() {
        return etd;
    }

    public Trip etd(ZonedDateTime etd) {
        this.etd = etd;
        return this;
    }

    public void setEtd(ZonedDateTime etd) {
        this.etd = etd;
    }

    public String getDescription() {
        return description;
    }

    public Trip description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ETripState getState() {
        return state;
    }

    public Trip state(ETripState state) {
        this.state = state;
        return this;
    }

    public void setState(ETripState state) {
        this.state = state;
    }

    public ZonedDateTime getEta() {
        return eta;
    }

    public Trip eta(ZonedDateTime eta) {
        this.eta = eta;
        return this;
    }

    public void setEta(ZonedDateTime eta) {
        this.eta = eta;
    }

    public Integer getDistance() {
        return distance;
    }

    public Trip distance(Integer distance) {
        this.distance = distance;
        return this;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Origin getOrigin() {
        return origin;
    }

    public Trip origin(Origin origin) {
        this.origin = origin;
        return this;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public Destination getDestination() {
        return destination;
    }

    public Trip destination(Destination destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Set<Conversation> getConversations() {
        return conversations;
    }

    public Trip conversations(Set<Conversation> conversations) {
        this.conversations = conversations;
        return this;
    }

    public Trip addConversation(Conversation conversation) {
        this.conversations.add(conversation);
        conversation.setTrip(this);
        return this;
    }

    public Trip removeConversation(Conversation conversation) {
        this.conversations.remove(conversation);
        conversation.setTrip(null);
        return this;
    }

    public void setConversations(Set<Conversation> conversations) {
        this.conversations = conversations;
    }

    public Truck getTruck() {
        return truck;
    }

    public Trip truck(Truck truck) {
        this.truck = truck;
        return this;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public ClientAccount getClientAccount() {
        return clientAccount;
    }

    public Trip clientAccount(ClientAccount clientAccount) {
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
        if (!(o instanceof Trip)) {
            return false;
        }
        return id != null && id.equals(((Trip) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Trip{" +
            "id=" + getId() +
            ", isFull='" + isIsFull() + "'" +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            ", length=" + getLength() +
            ", weight=" + getWeight() +
            ", marchandise='" + getMarchandise() + "'" +
            ", etd='" + getEtd() + "'" +
            ", description='" + getDescription() + "'" +
            ", state='" + getState() + "'" +
            ", eta='" + getEta() + "'" +
            ", distance=" + getDistance() +
            "}";
    }
}
