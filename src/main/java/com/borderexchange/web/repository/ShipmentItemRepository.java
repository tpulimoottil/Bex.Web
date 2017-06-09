package com.borderexchange.web.repository;

import com.borderexchange.web.domain.ShipmentItem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the ShipmentItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentItemRepository extends JpaRepository<ShipmentItem,Long> {

    @Query("select shipment_item from ShipmentItem shipment_item where shipment_item.createdBy.login = ?#{principal.username}")
    List<ShipmentItem> findByCreatedByIsCurrentUser();

    @Query("select shipment_item from ShipmentItem shipment_item where shipment_item.user.login = ?#{principal.username}")
    List<ShipmentItem> findByUserIsCurrentUser();

}
