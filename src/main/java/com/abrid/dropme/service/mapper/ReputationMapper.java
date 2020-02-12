package com.abrid.dropme.service.mapper;


import com.abrid.dropme.domain.*;
import com.abrid.dropme.service.dto.ReputationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reputation} and its DTO {@link ReputationDTO}.
 */
@Mapper(componentModel = "spring", uses = {TransporterAccountMapper.class, ClientAccountMapper.class})
public interface ReputationMapper extends EntityMapper<ReputationDTO, Reputation> {

    @Mapping(source = "transporterAccount.id", target = "transporterAccountId")
    @Mapping(source = "clientAccount.id", target = "clientAccountId")
    ReputationDTO toDto(Reputation reputation);

    @Mapping(source = "transporterAccountId", target = "transporterAccount")
    @Mapping(source = "clientAccountId", target = "clientAccount")
    Reputation toEntity(ReputationDTO reputationDTO);

    default Reputation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reputation reputation = new Reputation();
        reputation.setId(id);
        return reputation;
    }
}
