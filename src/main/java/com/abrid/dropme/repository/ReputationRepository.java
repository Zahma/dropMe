package com.abrid.dropme.repository;

import com.abrid.dropme.domain.Reputation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Reputation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReputationRepository extends JpaRepository<Reputation, Long>, JpaSpecificationExecutor<Reputation> {

}
