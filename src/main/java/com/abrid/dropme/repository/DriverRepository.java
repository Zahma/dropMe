package com.abrid.dropme.repository;

import com.abrid.dropme.domain.Driver;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Driver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long>, JpaSpecificationExecutor<Driver> {

}
