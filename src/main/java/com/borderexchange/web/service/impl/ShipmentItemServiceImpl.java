package com.borderexchange.web.service.impl;

import com.borderexchange.web.service.ShipmentItemService;
import com.borderexchange.web.domain.ShipmentItem;
import com.borderexchange.web.repository.ShipmentItemRepository;
import com.borderexchange.web.service.dto.ShipmentItemDTO;
import com.borderexchange.web.service.mapper.ShipmentItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ShipmentItem.
 */
@Service
@Transactional
public class ShipmentItemServiceImpl implements ShipmentItemService{

    private final Logger log = LoggerFactory.getLogger(ShipmentItemServiceImpl.class);

    private final ShipmentItemRepository shipmentItemRepository;

    private final ShipmentItemMapper shipmentItemMapper;

    public ShipmentItemServiceImpl(ShipmentItemRepository shipmentItemRepository, ShipmentItemMapper shipmentItemMapper) {
        this.shipmentItemRepository = shipmentItemRepository;
        this.shipmentItemMapper = shipmentItemMapper;
    }

    /**
     * Save a shipmentItem.
     *
     * @param shipmentItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipmentItemDTO save(ShipmentItemDTO shipmentItemDTO) {
        log.debug("Request to save ShipmentItem : {}", shipmentItemDTO);
        ShipmentItem shipmentItem = shipmentItemMapper.toEntity(shipmentItemDTO);
        shipmentItem = shipmentItemRepository.save(shipmentItem);
        return shipmentItemMapper.toDto(shipmentItem);
    }

    /**
     *  Get all the shipmentItems.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShipmentItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShipmentItems");
        return shipmentItemRepository.findAll(pageable)
            .map(shipmentItemMapper::toDto);
    }

    /**
     *  Get one shipmentItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShipmentItemDTO findOne(Long id) {
        log.debug("Request to get ShipmentItem : {}", id);
        ShipmentItem shipmentItem = shipmentItemRepository.findOne(id);
        return shipmentItemMapper.toDto(shipmentItem);
    }

    /**
     *  Delete the  shipmentItem by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipmentItem : {}", id);
        shipmentItemRepository.delete(id);
    }
}
