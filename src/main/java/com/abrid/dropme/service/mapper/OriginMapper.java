package com.abrid.dropme.service.mapper;


import com.abrid.dropme.domain.*;
import com.abrid.dropme.service.dto.OriginDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Origin} and its DTO {@link OriginDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OriginMapper extends EntityMapper<OriginDTO, Origin> {


    @Mapping(target = "location", ignore = true)
    @Mapping(target = "trip", ignore = true)
    Origin toEntity(OriginDTO originDTO);

    default Origin fromId(Long id) {
        if (id == null) {
            return null;
        }
        Origin origin = new Origin();
        origin.setId(id);
        return origin;
    }
}
