package org.swisscom.serviceapp.mapper;

import org.swisscom.serviceapp.model.Owner;
import org.swisscom.serviceapp.model.dto.OwnerDTO;

import java.util.UUID;

public class OwnerMapper {

    private OwnerMapper() {
        // not-instantiable
    }
    public static OwnerDTO toDTO(Owner owner) {
        OwnerDTO ownerDTO = new OwnerDTO();

        ownerDTO.setId(owner.getId().toString());
        ownerDTO.setName(owner.getName());
        ownerDTO.setAccountNumber(owner.getAccountNumber());
        ownerDTO.setLevel(owner.getLevel());

        return ownerDTO;
    }

    public static Owner toEntity(OwnerDTO ownerDTO) {
        Owner owner = new Owner();

        owner.setId(UUID.randomUUID());
        owner.setName(ownerDTO.getName());
        owner.setAccountNumber(ownerDTO.getAccountNumber());
        owner.setLevel(ownerDTO.getLevel());

        return owner;
    }
}
