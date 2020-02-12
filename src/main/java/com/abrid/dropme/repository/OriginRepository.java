package com.abrid.dropme.repository;

import com.abrid.dropme.domain.Origin;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Origin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OriginRepository extends JpaRepository<Origin, Long>, JpaSpecificationExecutor<Origin> {

}
