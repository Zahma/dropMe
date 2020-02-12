package com.abrid.dropme.service.mapper;


import com.abrid.dropme.domain.*;
import com.abrid.dropme.service.dto.TripDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Trip} and its DTO {@link TripDTO}.
 */
@Mapper(componentModel = "spring", uses = {OriginMapper.class, DestinationMapper.class, ClientAccountMapper.class})
public interface TripMapper extends EntityMapper<TripDTO, Trip> {

    @Mapping(source = "origin.id", target = "originId")
    @Mapping(source = "destination.id", target = "destinationId")
    @Mapping(source = "clientAccount.id", target = "clientAccountId")
    TripDTO toDto(Trip trip);

    @Mapping(source = "originId", target = "origin")
    @Mapping(source = "destinationId", target = "destination")
    @Mapping(target = "conversations", ignore = true)
    @Mapping(target = "removeConversation", ignore = true)
    @Mapping(target = "truck", ignore = true)
    @Mapping(source = "clientAccountId", target = "clientAccount")
    Trip toEntity(TripDTO tripDTO);

    default Trip fromId(Long id) {
        if (id == null) {
            return null;
        }
        Trip trip = new Trip();
        trip.setId(id);
        return trip;
    }
}
