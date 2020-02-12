package com.abrid.dropme.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.abrid.dropme.domain.enumeration.EMarchandise;
import com.abrid.dropme.domain.enumeration.ETripState;

/**
 * A DTO for the {@link com.abrid.dropme.domain.Trip} entity.
 */
public class TripDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean isFull;

    private Integer width;

    private Integer height;

    private Integer length;

    private Integer weight;

    @NotNull
    private EMarchandise marchandise;

    @NotNull
    private ZonedDateTime etd;

    private String description;

    private ETripState state;

    private ZonedDateTime eta;

    @NotNull
    private Integer distance;


    private Long originId;

    private Long destinationId;

    private Long clientAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsFull() {
        return isFull;
    }

    public void setIsFull(Boolean isFull) {
        this.isFull = isFull;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public EMarchandise getMarchandise() {
        return marchandise;
    }

    public void setMarchandise(EMarchandise marchandise) {
        this.marchandise = marchandise;
    }

    public ZonedDateTime getEtd() {
        return etd;
    }

    public void setEtd(ZonedDateTime etd) {
        this.etd = etd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ETripState getState() {
        return state;
    }

    public void setState(ETripState state) {
        this.state = state;
    }

    public ZonedDateTime getEta() {
        return eta;
    }

    public void setEta(ZonedDateTime eta) {
        this.eta = eta;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
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

        TripDTO tripDTO = (TripDTO) o;
        if (tripDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tripDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TripDTO{" +
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
            ", originId=" + getOriginId() +
            ", destinationId=" + getDestinationId() +
            ", clientAccountId=" + getClientAccountId() +
            "}";
    }
}
