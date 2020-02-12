package com.abrid.dropme.service.mapper;


import com.abrid.dropme.domain.*;
import com.abrid.dropme.service.dto.LocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {OriginMapper.class, DestinationMapper.class, CountryMapper.class})
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {

    @Mapping(source = "origin.id", target = "originId")
    @Mapping(source = "destination.id", target = "destinationId")
    @Mapping(source = "country.id", target = "countryId")
    LocationDTO toDto(Location location);

    @Mapping(source = "originId", target = "origin")
    @Mapping(source = "destinationId", target = "destination")
    @Mapping(source = "countryId", target = "country")
    Location toEntity(LocationDTO locationDTO);

    default Location fromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }
}
