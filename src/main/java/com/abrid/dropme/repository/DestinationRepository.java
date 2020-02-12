package com.abrid.dropme.repository;

import com.abrid.dropme.domain.Destination;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Destination entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long>, JpaSpecificationExecutor<Destination> {

}
