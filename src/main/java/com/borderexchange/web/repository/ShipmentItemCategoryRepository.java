package com.borderexchange.web.repository;

import com.borderexchange.web.domain.ShipmentItemCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ShipmentItemCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentItemCategoryRepository extends JpaRepository<ShipmentItemCategory,Long> {

}
