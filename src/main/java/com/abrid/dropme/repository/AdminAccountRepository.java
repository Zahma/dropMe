package com.abrid.dropme.repository;

import com.abrid.dropme.domain.AdminAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdminAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminAccountRepository extends JpaRepository<AdminAccount, Long>, JpaSpecificationExecutor<AdminAccount> {

}
