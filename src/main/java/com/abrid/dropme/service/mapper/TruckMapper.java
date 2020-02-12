package com.abrid.dropme.service.mapper;


import com.abrid.dropme.domain.*;
import com.abrid.dropme.service.dto.TruckDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Truck} and its DTO {@link TruckDTO}.
 */
@Mapper(componentModel = "spring", uses = {DriverMapper.class, TripMapper.class, TransporterAccountMapper.class})
public interface TruckMapper extends EntityMapper<TruckDTO, Truck> {

    @Mapping(source = "driver.id", target = "driverId")
    @Mapping(source = "trip.id", target = "tripId")
    @Mapping(source = "transporterAccount.id", target = "transporterAccountId")
    TruckDTO toDto(Truck truck);

    @Mapping(source = "driverId", target = "driver")
    @Mapping(source = "tripId", target = "trip")
    @Mapping(target = "conversations", ignore = true)
    @Mapping(target = "removeConversation", ignore = true)
    @Mapping(source = "transporterAccountId", target = "transporterAccount")
    Truck toEntity(TruckDTO truckDTO);

    default Truck fromId(Long id) {
        if (id == null) {
            return null;
        }
        Truck truck = new Truck();
        truck.setId(id);
        return truck;
    }
}
