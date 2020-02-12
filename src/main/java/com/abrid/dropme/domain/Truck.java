package com.abrid.dropme.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.abrid.dropme.domain.enumeration.ETruckType;

/**
 * A Truck.
 */
@Entity
@Table(name = "truck")
public class Truck implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "conteneur_plate_number")
    private String conteneurPlateNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ETruckType type;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "length")
    private Integer length;

    @Column(name = "max_weight")
    private Integer maxWeight;

    @OneToOne
    @JoinColumn(unique = true)
    private Driver driver;

    @OneToOne
    @JoinColumn(unique = true)
    private Trip trip;

    @OneToMany(mappedBy = "truck")
    private Set<Conversation> conversations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("trucks")
    private TransporterAccount transporterAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public Truck plateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
        return this;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getConteneurPlateNumber() {
        return conteneurPlateNumber;
    }

    public Truck conteneurPlateNumber(String conteneurPlateNumber) {
        this.conteneurPlateNumber = conteneurPlateNumber;
        return this;
    }

    public void setConteneurPlateNumber(String conteneurPlateNumber) {
        this.conteneurPlateNumber = conteneurPlateNumber;
    }

    public ETruckType getType() {
        return type;
    }

    public Truck type(ETruckType type) {
        this.type = type;
        return this;
    }

    public void setType(ETruckType type) {
        this.type = type;
    }

    public Integer getWidth() {
        return width;
    }

    public Truck width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public Truck height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLength() {
        return length;
    }

    public Truck length(Integer length) {
        this.length = length;
        return this;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getMaxWeight() {
        return maxWeight;
    }

    public Truck maxWeight(Integer maxWeight) {
        this.maxWeight = maxWeight;
        return this;
    }

    public void setMaxWeight(Integer maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Driver getDriver() {
        return driver;
    }

    public Truck driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Trip getTrip() {
        return trip;
    }

    public Truck trip(Trip trip) {
        this.trip = trip;
        return this;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Set<Conversation> getConversations() {
        return conversations;
    }

    public Truck conversations(Set<Conversation> conversations) {
        this.conversations = conversations;
        return this;
    }

    public Truck addConversation(Conversation conversation) {
        this.conversations.add(conversation);
        conversation.setTruck(this);
        return this;
    }

    public Truck removeConversation(Conversation conversation) {
        this.conversations.remove(conversation);
        conversation.setTruck(null);
        return this;
    }

    public void setConversations(Set<Conversation> conversations) {
        this.conversations = conversations;
    }

    public TransporterAccount getTransporterAccount() {
        return transporterAccount;
    }

    public Truck transporterAccount(TransporterAccount transporterAccount) {
        this.transporterAccount = transporterAccount;
        return this;
    }

    public void setTransporterAccount(TransporterAccount transporterAccount) {
        this.transporterAccount = transporterAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Truck)) {
            return false;
        }
        return id != null && id.equals(((Truck) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Truck{" +
            "id=" + getId() +
            ", plateNumber='" + getPlateNumber() + "'" +
            ", conteneurPlateNumber='" + getConteneurPlateNumber() + "'" +
            ", type='" + getType() + "'" +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            ", length=" + getLength() +
            ", maxWeight=" + getMaxWeight() +
            "}";
    }
}
