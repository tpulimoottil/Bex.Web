package com.borderexchange.web.service.mapper;

import com.borderexchange.web.domain.*;
import com.borderexchange.web.service.dto.UserAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserAddress and its DTO UserAddressDTO.
 */
@Mapper(componentModel = "spring", uses = {StateMapper.class, UserMapper.class, })
public interface UserAddressMapper extends EntityMapper <UserAddressDTO, UserAddress> {

    @Mapping(source = "state.id", target = "stateId")

    @Mapping(source = "user.id", target = "userId")
    UserAddressDTO toDto(UserAddress userAddress); 

    @Mapping(source = "stateId", target = "state")

    @Mapping(source = "userId", target = "user")
    UserAddress toEntity(UserAddressDTO userAddressDTO); 
    default UserAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserAddress userAddress = new UserAddress();
        userAddress.setId(id);
        return userAddress;
    }
}
