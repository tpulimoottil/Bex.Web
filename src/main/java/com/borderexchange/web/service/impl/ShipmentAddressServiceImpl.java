package com.borderexchange.web.service.impl;

import com.borderexchange.web.service.ShipmentAddressService;
import com.borderexchange.web.domain.ShipmentAddress;
import com.borderexchange.web.repository.ShipmentAddressRepository;
import com.borderexchange.web.service.dto.ShipmentAddressDTO;
import com.borderexchange.web.service.mapper.ShipmentAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ShipmentAddress.
 */
@Service
@Transactional
public class ShipmentAddressServiceImpl implements ShipmentAddressService{

    private final Logger log = LoggerFactory.getLogger(ShipmentAddressServiceImpl.class);

    private final ShipmentAddressRepository shipmentAddressRepository;

    private final ShipmentAddressMapper shipmentAddressMapper;

    public ShipmentAddressServiceImpl(ShipmentAddressRepository shipmentAddressRepository, ShipmentAddressMapper shipmentAddressMapper) {
        this.shipmentAddressRepository = shipmentAddressRepository;
        this.shipmentAddressMapper = shipmentAddressMapper;
    }

    /**
     * Save a shipmentAddress.
     *
     * @param shipmentAddressDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipmentAddressDTO save(ShipmentAddressDTO shipmentAddressDTO) {
        log.debug("Request to save ShipmentAddress : {}", shipmentAddressDTO);
        ShipmentAddress shipmentAddress = shipmentAddressMapper.toEntity(shipmentAddressDTO);
        shipmentAddress = shipmentAddressRepository.save(shipmentAddress);
        return shipmentAddressMapper.toDto(shipmentAddress);
    }

    /**
     *  Get all the shipmentAddresses.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentAddressDTO> findAll() {
        log.debug("Request to get all ShipmentAddresses");
        return shipmentAddressRepository.findAll().stream()
            .map(shipmentAddressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one shipmentAddress by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShipmentAddressDTO findOne(Long id) {
        log.debug("Request to get ShipmentAddress : {}", id);
        ShipmentAddress shipmentAddress = shipmentAddressRepository.findOne(id);
        return shipmentAddressMapper.toDto(shipmentAddress);
    }

    /**
     *  Delete the  shipmentAddress by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipmentAddress : {}", id);
        shipmentAddressRepository.delete(id);
    }
}
