package com.borderexchange.web.service.impl;

import com.borderexchange.web.service.ShipmentItemCategoryService;
import com.borderexchange.web.domain.ShipmentItemCategory;
import com.borderexchange.web.repository.ShipmentItemCategoryRepository;
import com.borderexchange.web.service.dto.ShipmentItemCategoryDTO;
import com.borderexchange.web.service.mapper.ShipmentItemCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ShipmentItemCategory.
 */
@Service
@Transactional
public class ShipmentItemCategoryServiceImpl implements ShipmentItemCategoryService{

    private final Logger log = LoggerFactory.getLogger(ShipmentItemCategoryServiceImpl.class);

    private final ShipmentItemCategoryRepository shipmentItemCategoryRepository;

    private final ShipmentItemCategoryMapper shipmentItemCategoryMapper;

    public ShipmentItemCategoryServiceImpl(ShipmentItemCategoryRepository shipmentItemCategoryRepository, ShipmentItemCategoryMapper shipmentItemCategoryMapper) {
        this.shipmentItemCategoryRepository = shipmentItemCategoryRepository;
        this.shipmentItemCategoryMapper = shipmentItemCategoryMapper;
    }

    /**
     * Save a shipmentItemCategory.
     *
     * @param shipmentItemCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipmentItemCategoryDTO save(ShipmentItemCategoryDTO shipmentItemCategoryDTO) {
        log.debug("Request to save ShipmentItemCategory : {}", shipmentItemCategoryDTO);
        ShipmentItemCategory shipmentItemCategory = shipmentItemCategoryMapper.toEntity(shipmentItemCategoryDTO);
        shipmentItemCategory = shipmentItemCategoryRepository.save(shipmentItemCategory);
        return shipmentItemCategoryMapper.toDto(shipmentItemCategory);
    }

    /**
     *  Get all the shipmentItemCategories.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentItemCategoryDTO> findAll() {
        log.debug("Request to get all ShipmentItemCategories");
        return shipmentItemCategoryRepository.findAll().stream()
            .map(shipmentItemCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one shipmentItemCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShipmentItemCategoryDTO findOne(Long id) {
        log.debug("Request to get ShipmentItemCategory : {}", id);
        ShipmentItemCategory shipmentItemCategory = shipmentItemCategoryRepository.findOne(id);
        return shipmentItemCategoryMapper.toDto(shipmentItemCategory);
    }

    /**
     *  Delete the  shipmentItemCategory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipmentItemCategory : {}", id);
        shipmentItemCategoryRepository.delete(id);
    }
}
