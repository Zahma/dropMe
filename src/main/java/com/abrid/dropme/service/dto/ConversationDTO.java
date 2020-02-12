package com.abrid.dropme.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.abrid.dropme.domain.Conversation} entity.
 */
public class ConversationDTO implements Serializable {

    private Long id;


    private Long tripId;

    private Long truckId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getTruckId() {
        return truckId;
    }

    public void setTruckId(Long truckId) {
        this.truckId = truckId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConversationDTO conversationDTO = (ConversationDTO) o;
        if (conversationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conversationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConversationDTO{" +
            "id=" + getId() +
            ", tripId=" + getTripId() +
            ", truckId=" + getTruckId() +
            "}";
    }
}
