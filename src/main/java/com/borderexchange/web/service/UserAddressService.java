package com.borderexchange.web.service;

import com.borderexchange.web.service.dto.UserAddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UserAddress.
 */
public interface UserAddressService {

    /**
     * Save a userAddress.
     *
     * @param userAddressDTO the entity to save
     * @return the persisted entity
     */
    UserAddressDTO save(UserAddressDTO userAddressDTO);

    /**
     *  Get all the userAddresses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UserAddressDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" userAddress.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserAddressDTO findOne(Long id);

    /**
     *  Delete the "id" userAddress.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
