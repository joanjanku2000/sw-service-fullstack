package org.swisscom.serviceapp.infrastructure.mapper;

import org.swisscom.serviceapp.domain.model.Owner;
import org.swisscom.serviceapp.infrastructure.dto.OwnerDto;

import java.util.List;
import java.util.UUID;

public class OwnerMapper {

    private OwnerMapper() {
        // not-instantiable
    }

    public static OwnerDto toDTO(final Owner owner) {
        return new OwnerDto(owner.getId().toString(), owner.getName(), owner.getAccountNumber(), owner.getLevel());
    }

    public static Owner toEntity(final OwnerDto ownerDto) {
        Owner owner = new Owner();

        owner.setId(UUID.randomUUID());
        owner.setName(ownerDto.name());
        owner.setAccountNumber(ownerDto.accountNumber());
        owner.setLevel(ownerDto.level());

        return owner;
    }

    public static Owner toEntityForUpdate(final OwnerDto ownerDto) {
        Owner owner = new Owner();

        owner.setId(ownerDto.id() != null && !ownerDto.id().isEmpty() ?
                UUID.fromString(ownerDto.id()) : UUID.randomUUID());

        owner.setName(ownerDto.name());
        owner.setAccountNumber(ownerDto.accountNumber());
        owner.setLevel(ownerDto.level());

        return owner;
    }

    public static List<Owner> toEntityListForUpdate(final List<OwnerDto> ownerDtoList) {
       return ownerDtoList.stream().map(OwnerMapper::toEntityForUpdate).toList();
    }

    public static List<Owner> toEntityList(final List<OwnerDto> ownerDtoList) {
        return ownerDtoList.stream().map(OwnerMapper::toEntity).toList();
    }

    public static List<OwnerDto> toDtoList(final List<Owner> ownersList) {
        return ownersList.stream().map(OwnerMapper::toDTO).toList();
    }
}
