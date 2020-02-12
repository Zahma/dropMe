package com.abrid.dropme.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Conversation.
 */
@Entity
@Table(name = "conversation")
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "conversation")
    private Set<Chat> chats = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("conversations")
    private Trip trip;

    @ManyToOne
    @JsonIgnoreProperties("conversations")
    private Truck truck;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public Conversation chats(Set<Chat> chats) {
        this.chats = chats;
        return this;
    }

    public Conversation addChat(Chat chat) {
        this.chats.add(chat);
        chat.setConversation(this);
        return this;
    }

    public Conversation removeChat(Chat chat) {
        this.chats.remove(chat);
        chat.setConversation(null);
        return this;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }

    public Trip getTrip() {
        return trip;
    }

    public Conversation trip(Trip trip) {
        this.trip = trip;
        return this;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Truck getTruck() {
        return truck;
    }

    public Conversation truck(Truck truck) {
        this.truck = truck;
        return this;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conversation)) {
            return false;
        }
        return id != null && id.equals(((Conversation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Conversation{" +
            "id=" + getId() +
            "}";
    }
}
