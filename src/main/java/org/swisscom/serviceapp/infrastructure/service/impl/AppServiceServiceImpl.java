package org.swisscom.serviceapp.infrastructure.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.swisscom.serviceapp.domain.model.AppService;
import org.swisscom.serviceapp.domain.repo.AppServiceRepository;
import org.swisscom.serviceapp.infrastructure.api.exception.ExceptionMessage;
import org.swisscom.serviceapp.infrastructure.api.exception.NotFoundException;
import org.swisscom.serviceapp.infrastructure.dto.AppServiceDTO;
import org.swisscom.serviceapp.infrastructure.mapper.ServiceMapper;
import org.swisscom.serviceapp.infrastructure.service.AppServiceService;

import java.util.UUID;

@Service
public class AppServiceServiceImpl implements AppServiceService {
    static final Logger logger = LoggerFactory.getLogger(AppServiceServiceImpl.class);
    private static final String SERVICE_ENTITY_NAME = "Service";
    private final AppServiceRepository serviceRepository;


    public AppServiceServiceImpl(AppServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public AppServiceDTO save(AppServiceDTO appServiceDTO) {
        return ServiceMapper.toDTO(serviceRepository.save(ServiceMapper.toEntity(appServiceDTO)));
    }

    @Override
    public AppServiceDTO update(UUID id, AppServiceDTO appServiceDTO) {
        AppService appService = serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.NOT_FOUND.getMessage(), SERVICE_ENTITY_NAME, id)));

        return ServiceMapper.toDTO(serviceRepository.save(ServiceMapper.toEntityForUpdate(appService, appServiceDTO)));
    }

    @Override
    public AppServiceDTO findById(UUID id) {
        logger.debug("Searching for service with id {}", id);
        return ServiceMapper.toDTO(serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.NOT_FOUND.getMessage(), SERVICE_ENTITY_NAME, id))));
    }
}
