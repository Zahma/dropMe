package com.abrid.dropme.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.abrid.dropme.domain.Origin} entity.
 */
public class OriginDTO implements Serializable {

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

        OriginDTO originDTO = (OriginDTO) o;
        if (originDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), originDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OriginDTO{" +
            "id=" + getId() +
            "}";
    }
}
