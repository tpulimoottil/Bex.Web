package com.borderexchange.web.service.impl;

import com.borderexchange.web.service.ShipmentOrderAuditService;
import com.borderexchange.web.domain.ShipmentOrderAudit;
import com.borderexchange.web.repository.ShipmentOrderAuditRepository;
import com.borderexchange.web.service.dto.ShipmentOrderAuditDTO;
import com.borderexchange.web.service.mapper.ShipmentOrderAuditMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ShipmentOrderAudit.
 */
@Service
@Transactional
public class ShipmentOrderAuditServiceImpl implements ShipmentOrderAuditService{

    private final Logger log = LoggerFactory.getLogger(ShipmentOrderAuditServiceImpl.class);

    private final ShipmentOrderAuditRepository shipmentOrderAuditRepository;

    private final ShipmentOrderAuditMapper shipmentOrderAuditMapper;

    public ShipmentOrderAuditServiceImpl(ShipmentOrderAuditRepository shipmentOrderAuditRepository, ShipmentOrderAuditMapper shipmentOrderAuditMapper) {
        this.shipmentOrderAuditRepository = shipmentOrderAuditRepository;
        this.shipmentOrderAuditMapper = shipmentOrderAuditMapper;
    }

    /**
     * Save a shipmentOrderAudit.
     *
     * @param shipmentOrderAuditDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipmentOrderAuditDTO save(ShipmentOrderAuditDTO shipmentOrderAuditDTO) {
        log.debug("Request to save ShipmentOrderAudit : {}", shipmentOrderAuditDTO);
        ShipmentOrderAudit shipmentOrderAudit = shipmentOrderAuditMapper.toEntity(shipmentOrderAuditDTO);
        shipmentOrderAudit = shipmentOrderAuditRepository.save(shipmentOrderAudit);
        return shipmentOrderAuditMapper.toDto(shipmentOrderAudit);
    }

    /**
     *  Get all the shipmentOrderAudits.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentOrderAuditDTO> findAll() {
        log.debug("Request to get all ShipmentOrderAudits");
        return shipmentOrderAuditRepository.findAll().stream()
            .map(shipmentOrderAuditMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one shipmentOrderAudit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShipmentOrderAuditDTO findOne(Long id) {
        log.debug("Request to get ShipmentOrderAudit : {}", id);
        ShipmentOrderAudit shipmentOrderAudit = shipmentOrderAuditRepository.findOne(id);
        return shipmentOrderAuditMapper.toDto(shipmentOrderAudit);
    }

    /**
     *  Delete the  shipmentOrderAudit by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipmentOrderAudit : {}", id);
        shipmentOrderAuditRepository.delete(id);
    }
}
