package com.abrid.dropme.service.mapper;


import com.abrid.dropme.domain.*;
import com.abrid.dropme.service.dto.DestinationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Destination} and its DTO {@link DestinationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DestinationMapper extends EntityMapper<DestinationDTO, Destination> {


    @Mapping(target = "location", ignore = true)
    @Mapping(target = "trip", ignore = true)
    Destination toEntity(DestinationDTO destinationDTO);

    default Destination fromId(Long id) {
        if (id == null) {
            return null;
        }
        Destination destination = new Destination();
        destination.setId(id);
        return destination;
    }
}
