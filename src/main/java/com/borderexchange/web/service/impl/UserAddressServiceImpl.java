package com.borderexchange.web.service.impl;

import com.borderexchange.web.service.UserAddressService;
import com.borderexchange.web.domain.UserAddress;
import com.borderexchange.web.repository.UserAddressRepository;
import com.borderexchange.web.service.dto.UserAddressDTO;
import com.borderexchange.web.service.mapper.UserAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UserAddress.
 */
@Service
@Transactional
public class UserAddressServiceImpl implements UserAddressService{

    private final Logger log = LoggerFactory.getLogger(UserAddressServiceImpl.class);

    private final UserAddressRepository userAddressRepository;

    private final UserAddressMapper userAddressMapper;

    public UserAddressServiceImpl(UserAddressRepository userAddressRepository, UserAddressMapper userAddressMapper) {
        this.userAddressRepository = userAddressRepository;
        this.userAddressMapper = userAddressMapper;
    }

    /**
     * Save a userAddress.
     *
     * @param userAddressDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserAddressDTO save(UserAddressDTO userAddressDTO) {
        log.debug("Request to save UserAddress : {}", userAddressDTO);
        UserAddress userAddress = userAddressMapper.toEntity(userAddressDTO);
        userAddress = userAddressRepository.save(userAddress);
        return userAddressMapper.toDto(userAddress);
    }

    /**
     *  Get all the userAddresses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserAddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserAddresses");
        return userAddressRepository.findAll(pageable)
            .map(userAddressMapper::toDto);
    }

    /**
     *  Get one userAddress by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserAddressDTO findOne(Long id) {
        log.debug("Request to get UserAddress : {}", id);
        UserAddress userAddress = userAddressRepository.findOne(id);
        return userAddressMapper.toDto(userAddress);
    }

    /**
     *  Delete the  userAddress by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserAddress : {}", id);
        userAddressRepository.delete(id);
    }
}
