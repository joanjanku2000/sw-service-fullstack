package org.swisscom.serviceapp.infrastructure.service.impl;


import org.apache.logging.log4j.simple.SimpleLoggerContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.swisscom.serviceapp.domain.model.AppService;
import org.swisscom.serviceapp.domain.repo.AppServiceRepository;
import org.swisscom.serviceapp.infrastructure.api.exception.ExceptionMessage;
import org.swisscom.serviceapp.infrastructure.api.exception.NotFoundException;
import org.swisscom.serviceapp.infrastructure.dto.AppServiceDto;
import org.swisscom.serviceapp.infrastructure.mapper.ServiceMapper;
import org.swisscom.serviceapp.infrastructure.service.AppServiceService;

import java.util.UUID;

@Service
public class AppServiceServiceImpl implements AppServiceService {
    final Logger log = LoggerFactory.getLogger(AppServiceServiceImpl.class);
    private static final String SERVICE_ENTITY_NAME = "Service";
    private final AppServiceRepository serviceRepository;
    
    public AppServiceServiceImpl(AppServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @CachePut(cacheNames = "app-service",key = "#appServiceDTO.id()")
    @Override
    public AppServiceDto save(final AppServiceDto appServiceDTO) {
        return ServiceMapper.toDTO(serviceRepository.save(ServiceMapper.toEntity(appServiceDTO)));
    }

    @CachePut(cacheNames = "app-service",key = "#id")
    @Override
    public AppServiceDto update(final UUID id, final AppServiceDto appServiceDTO) {
        AppService appService = serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.NOT_FOUND.getMessage(), SERVICE_ENTITY_NAME, id)));

        return ServiceMapper.toDTO(serviceRepository.save(ServiceMapper.toEntityForUpdate(appService, appServiceDTO)));
    }

    @Cacheable("app-service")
    @Override
    public AppServiceDto findById(final UUID id) {
        log.debug("Searching for service with id {}", id);
        log.info("Execitomg");
        return ServiceMapper.toDTO(serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.NOT_FOUND.getMessage(), SERVICE_ENTITY_NAME, id))));
    }
}
