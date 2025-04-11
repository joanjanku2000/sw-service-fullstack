package org.swisscom.serviceapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.swisscom.serviceapp.mapper.ServiceMapper;
import org.swisscom.serviceapp.model.AppService;
import org.swisscom.serviceapp.model.dto.AppServiceDTO;
import org.swisscom.serviceapp.repo.AppServiceRepository;
import org.swisscom.serviceapp.service.AppServiceService;

import java.util.UUID;

@Service
public class AppServiceServiceImpl implements AppServiceService {

    static final Logger logger = LoggerFactory.getLogger(AppServiceServiceImpl.class);
    private final AppServiceRepository serviceRepository;

    public AppServiceServiceImpl(AppServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public AppServiceDTO save(AppServiceDTO appServiceDTO) {
        return ServiceMapper.toDTO(serviceRepository.save(ServiceMapper.toEntity(appServiceDTO)));
    }

    @Override
    @Cacheable
    public AppServiceDTO update(UUID id, AppServiceDTO appServiceDTO) {
        AppService appService = serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("TO BE REPLACED WITH CUSTOM EXCEPTION"));

        return ServiceMapper.toDTO(serviceRepository.save(ServiceMapper.toEntityForUpdate(appService, appServiceDTO)));
    }

    @Override
    public AppServiceDTO findById(UUID id) {
        logger.debug("Searching for service with id {}",id);
        return ServiceMapper.toDTO(serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("TO BE REPLACED WITH CUSTOM EXCEPTION")));
    }
}
