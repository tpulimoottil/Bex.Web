package com.borderexchange.web.repository;

import com.borderexchange.web.domain.ShipmentItemProperties;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ShipmentItemProperties entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentItemPropertiesRepository extends JpaRepository<ShipmentItemProperties,Long> {

}
