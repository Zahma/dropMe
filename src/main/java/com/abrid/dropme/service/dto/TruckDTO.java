package com.abrid.dropme.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.abrid.dropme.domain.enumeration.ETruckType;

/**
 * A DTO for the {@link com.abrid.dropme.domain.Truck} entity.
 */
public class TruckDTO implements Serializable {

    private Long id;

    private String plateNumber;

    private String conteneurPlateNumber;

    private ETruckType type;

    private Integer width;

    private Integer height;

    private Integer length;

    private Integer maxWeight;


    private Long driverId;

    private Long tripId;

    private Long transporterAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getConteneurPlateNumber() {
        return conteneurPlateNumber;
    }

    public void setConteneurPlateNumber(String conteneurPlateNumber) {
        this.conteneurPlateNumber = conteneurPlateNumber;
    }

    public ETruckType getType() {
        return type;
    }

    public void setType(ETruckType type) {
        this.type = type;
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

    public Integer getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Integer maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getTransporterAccountId() {
        return transporterAccountId;
    }

    public void setTransporterAccountId(Long transporterAccountId) {
        this.transporterAccountId = transporterAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TruckDTO truckDTO = (TruckDTO) o;
        if (truckDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), truckDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TruckDTO{" +
            "id=" + getId() +
            ", plateNumber='" + getPlateNumber() + "'" +
            ", conteneurPlateNumber='" + getConteneurPlateNumber() + "'" +
            ", type='" + getType() + "'" +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            ", length=" + getLength() +
            ", maxWeight=" + getMaxWeight() +
            ", driverId=" + getDriverId() +
            ", tripId=" + getTripId() +
            ", transporterAccountId=" + getTransporterAccountId() +
            "}";
    }
}
