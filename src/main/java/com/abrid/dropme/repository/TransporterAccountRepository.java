package com.abrid.dropme.repository;

import com.abrid.dropme.domain.TransporterAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TransporterAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransporterAccountRepository extends JpaRepository<TransporterAccount, Long>, JpaSpecificationExecutor<TransporterAccount> {

}
