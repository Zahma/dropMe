package com.abrid.dropme.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.abrid.dropme.domain.Destination} entity.
 */
public class DestinationDTO implements Serializable {

    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DestinationDTO destinationDTO = (DestinationDTO) o;
        if (destinationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), destinationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DestinationDTO{" +
            "id=" + getId() +
            "}";
    }
}
