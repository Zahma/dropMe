package com.abrid.dropme.service.mapper;


import com.abrid.dropme.domain.*;
import com.abrid.dropme.service.dto.TransporterAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransporterAccount} and its DTO {@link TransporterAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TransporterAccountMapper extends EntityMapper<TransporterAccountDTO, TransporterAccount> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    TransporterAccountDTO toDto(TransporterAccount transporterAccount);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "trucks", ignore = true)
    @Mapping(target = "removeTruck", ignore = true)
    @Mapping(target = "reputations", ignore = true)
    @Mapping(target = "removeReputation", ignore = true)
    TransporterAccount toEntity(TransporterAccountDTO transporterAccountDTO);

    default TransporterAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransporterAccount transporterAccount = new TransporterAccount();
        transporterAccount.setId(id);
        return transporterAccount;
    }
}
