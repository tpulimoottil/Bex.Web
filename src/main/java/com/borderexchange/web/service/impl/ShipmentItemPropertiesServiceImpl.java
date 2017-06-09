package com.borderexchange.web.service.impl;

import com.borderexchange.web.service.ShipmentItemPropertiesService;
import com.borderexchange.web.domain.ShipmentItemProperties;
import com.borderexchange.web.repository.ShipmentItemPropertiesRepository;
import com.borderexchange.web.service.dto.ShipmentItemPropertiesDTO;
import com.borderexchange.web.service.mapper.ShipmentItemPropertiesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ShipmentItemProperties.
 */
@Service
@Transactional
public class ShipmentItemPropertiesServiceImpl implements ShipmentItemPropertiesService{

    private final Logger log = LoggerFactory.getLogger(ShipmentItemPropertiesServiceImpl.class);

    private final ShipmentItemPropertiesRepository shipmentItemPropertiesRepository;

    private final ShipmentItemPropertiesMapper shipmentItemPropertiesMapper;

    public ShipmentItemPropertiesServiceImpl(ShipmentItemPropertiesRepository shipmentItemPropertiesRepository, ShipmentItemPropertiesMapper shipmentItemPropertiesMapper) {
        this.shipmentItemPropertiesRepository = shipmentItemPropertiesRepository;
        this.shipmentItemPropertiesMapper = shipmentItemPropertiesMapper;
    }

    /**
     * Save a shipmentItemProperties.
     *
     * @param shipmentItemPropertiesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipmentItemPropertiesDTO save(ShipmentItemPropertiesDTO shipmentItemPropertiesDTO) {
        log.debug("Request to save ShipmentItemProperties : {}", shipmentItemPropertiesDTO);
        ShipmentItemProperties shipmentItemProperties = shipmentItemPropertiesMapper.toEntity(shipmentItemPropertiesDTO);
        shipmentItemProperties = shipmentItemPropertiesRepository.save(shipmentItemProperties);
        return shipmentItemPropertiesMapper.toDto(shipmentItemProperties);
    }

    /**
     *  Get all the shipmentItemProperties.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentItemPropertiesDTO> findAll() {
        log.debug("Request to get all ShipmentItemProperties");
        return shipmentItemPropertiesRepository.findAll().stream()
            .map(shipmentItemPropertiesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one shipmentItemProperties by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShipmentItemPropertiesDTO findOne(Long id) {
        log.debug("Request to get ShipmentItemProperties : {}", id);
        ShipmentItemProperties shipmentItemProperties = shipmentItemPropertiesRepository.findOne(id);
        return shipmentItemPropertiesMapper.toDto(shipmentItemProperties);
    }

    /**
     *  Delete the  shipmentItemProperties by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipmentItemProperties : {}", id);
        shipmentItemPropertiesRepository.delete(id);
    }
}
