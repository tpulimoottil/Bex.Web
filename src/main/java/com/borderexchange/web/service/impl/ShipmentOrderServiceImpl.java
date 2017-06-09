package com.borderexchange.web.service.impl;

import com.borderexchange.web.service.ShipmentOrderService;
import com.borderexchange.web.domain.ShipmentOrder;
import com.borderexchange.web.repository.ShipmentOrderRepository;
import com.borderexchange.web.service.dto.ShipmentOrderDTO;
import com.borderexchange.web.service.mapper.ShipmentOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ShipmentOrder.
 */
@Service
@Transactional
public class ShipmentOrderServiceImpl implements ShipmentOrderService{

    private final Logger log = LoggerFactory.getLogger(ShipmentOrderServiceImpl.class);

    private final ShipmentOrderRepository shipmentOrderRepository;

    private final ShipmentOrderMapper shipmentOrderMapper;

    public ShipmentOrderServiceImpl(ShipmentOrderRepository shipmentOrderRepository, ShipmentOrderMapper shipmentOrderMapper) {
        this.shipmentOrderRepository = shipmentOrderRepository;
        this.shipmentOrderMapper = shipmentOrderMapper;
    }

    /**
     * Save a shipmentOrder.
     *
     * @param shipmentOrderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipmentOrderDTO save(ShipmentOrderDTO shipmentOrderDTO) {
        log.debug("Request to save ShipmentOrder : {}", shipmentOrderDTO);
        ShipmentOrder shipmentOrder = shipmentOrderMapper.toEntity(shipmentOrderDTO);
        shipmentOrder = shipmentOrderRepository.save(shipmentOrder);
        return shipmentOrderMapper.toDto(shipmentOrder);
    }

    /**
     *  Get all the shipmentOrders.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShipmentOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShipmentOrders");
        return shipmentOrderRepository.findAll(pageable)
            .map(shipmentOrderMapper::toDto);
    }

    /**
     *  Get one shipmentOrder by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShipmentOrderDTO findOne(Long id) {
        log.debug("Request to get ShipmentOrder : {}", id);
        ShipmentOrder shipmentOrder = shipmentOrderRepository.findOne(id);
        return shipmentOrderMapper.toDto(shipmentOrder);
    }

    /**
     *  Delete the  shipmentOrder by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipmentOrder : {}", id);
        shipmentOrderRepository.delete(id);
    }
}
