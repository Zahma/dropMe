package com.abrid.dropme.repository;

import com.abrid.dropme.domain.ClientAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClientAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientAccountRepository extends JpaRepository<ClientAccount, Long>, JpaSpecificationExecutor<ClientAccount> {

}
