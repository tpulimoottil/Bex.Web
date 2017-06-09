package com.borderexchange.web.repository;

import com.borderexchange.web.domain.ShipmentOrder;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the ShipmentOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentOrderRepository extends JpaRepository<ShipmentOrder,Long> {

    @Query("select shipment_order from ShipmentOrder shipment_order where shipment_order.user.login = ?#{principal.username}")
    List<ShipmentOrder> findByUserIsCurrentUser();

}
