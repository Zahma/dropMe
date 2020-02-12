package com.abrid.dropme.service.mapper;


import com.abrid.dropme.domain.*;
import com.abrid.dropme.service.dto.AdminAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminAccount} and its DTO {@link AdminAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AdminAccountMapper extends EntityMapper<AdminAccountDTO, AdminAccount> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    AdminAccountDTO toDto(AdminAccount adminAccount);

    @Mapping(source = "userId", target = "user")
    AdminAccount toEntity(AdminAccountDTO adminAccountDTO);

    default AdminAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdminAccount adminAccount = new AdminAccount();
        adminAccount.setId(id);
        return adminAccount;
    }
}
