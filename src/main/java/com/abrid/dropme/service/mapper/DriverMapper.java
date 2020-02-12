package com.abrid.dropme.service.mapper;


import com.abrid.dropme.domain.*;
import com.abrid.dropme.service.dto.DriverDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Driver} and its DTO {@link DriverDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DriverMapper extends EntityMapper<DriverDTO, Driver> {


    @Mapping(target = "truck", ignore = true)
    Driver toEntity(DriverDTO driverDTO);

    default Driver fromId(Long id) {
        if (id == null) {
            return null;
        }
        Driver driver = new Driver();
        driver.setId(id);
        return driver;
    }
}
