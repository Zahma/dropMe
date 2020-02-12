package com.abrid.dropme.repository;

import com.abrid.dropme.domain.Truck;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Truck entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TruckRepository extends JpaRepository<Truck, Long>, JpaSpecificationExecutor<Truck> {

}
