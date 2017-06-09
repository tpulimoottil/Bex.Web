package com.borderexchange.web.repository;

import com.borderexchange.web.domain.ShipmentOrderAudit;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the ShipmentOrderAudit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentOrderAuditRepository extends JpaRepository<ShipmentOrderAudit,Long> {

    @Query("select shipment_order_audit from ShipmentOrderAudit shipment_order_audit where shipment_order_audit.user.login = ?#{principal.username}")
    List<ShipmentOrderAudit> findByUserIsCurrentUser();

}
