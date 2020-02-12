package com.abrid.dropme.service.mapper;


import com.abrid.dropme.domain.*;
import com.abrid.dropme.service.dto.ClientAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClientAccount} and its DTO {@link ClientAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ClientAccountMapper extends EntityMapper<ClientAccountDTO, ClientAccount> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    ClientAccountDTO toDto(ClientAccount clientAccount);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "reputations", ignore = true)
    @Mapping(target = "removeReputation", ignore = true)
    @Mapping(target = "trips", ignore = true)
    @Mapping(target = "removeTrip", ignore = true)
    ClientAccount toEntity(ClientAccountDTO clientAccountDTO);

    default ClientAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClientAccount clientAccount = new ClientAccount();
        clientAccount.setId(id);
        return clientAccount;
    }
}
