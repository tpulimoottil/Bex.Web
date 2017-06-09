package com.borderexchange.web.repository;

import com.borderexchange.web.domain.ShipmentAddress;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ShipmentAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentAddressRepository extends JpaRepository<ShipmentAddress,Long> {

}
