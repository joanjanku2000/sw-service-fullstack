package org.swisscom.serviceapp.infrastructure.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.swisscom.serviceapp.domain.model.AppService;
import org.swisscom.serviceapp.domain.repo.AppServiceRepository;
import org.swisscom.serviceapp.infrastructure.api.exception.ExceptionMessage;
import org.swisscom.serviceapp.infrastructure.api.exception.NotFoundException;
import org.swisscom.serviceapp.infrastructure.dto.AppServiceDto;
import org.swisscom.serviceapp.infrastructure.dto.RestPage;
import org.swisscom.serviceapp.infrastructure.mapper.AppServiceMapper;
import org.swisscom.serviceapp.infrastructure.mapper.ResourceMapper;
import org.swisscom.serviceapp.infrastructure.service.AppServiceService;

import java.util.ConcurrentModificationException;
import java.util.UUID;

import static org.swisscom.serviceapp.infrastructure.api.exception.ExceptionMessage.CONCURRENT_MODIFICATION;

@Service
public class AppServiceServiceImpl implements AppServiceService {
    // cache primarily for find-all
    private static final String CACHE_APP_SERVICE = "app_service";

    // cache primarily for find-by-id - individual documents
    private static final String CACHE_APP_SERVICES = "app-services";
    private static final String SERVICE_ENTITY_NAME = "Service";
    private static final String ID = "_id";
    private static final String VERSION = "version";
    private static final String RESOURCES = "resources";
    final Logger log = LoggerFactory.getLogger(AppServiceServiceImpl.class);
    private final AppServiceRepository serviceRepository;
    private final MongoTemplate mongoTemplate;

    public AppServiceServiceImpl(AppServiceRepository serviceRepository, MongoTemplate mongoTemplate) {
        this.serviceRepository = serviceRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Caching(
            evict = @CacheEvict(value = CACHE_APP_SERVICES, allEntries = true),
            put = @CachePut(cacheNames = CACHE_APP_SERVICE, key = "#result.id()")
    )
    @Override
    public AppServiceDto save(final AppServiceDto appServiceDTO) {
        return AppServiceMapper.toDTO(serviceRepository.save(AppServiceMapper.toEntity(appServiceDTO)));
    }

    /**
     * Implements a manual optimistic locking mechanism leveraging
     * mongotemplate
     */
    @Caching(
            put = @CachePut(cacheNames = CACHE_APP_SERVICE, key = "#id"),
            evict = @CacheEvict(value = CACHE_APP_SERVICES, allEntries = true),
            cacheable = @Cacheable(value = CACHE_APP_SERVICE, key = "#id")
    )
    @Override
    public AppServiceDto update(final UUID id, final AppServiceDto appServiceDTO) {

        ensureServiceExists(id);

        Query query = new Query(
                Criteria.where(ID).is(id).and(VERSION).is(appServiceDTO.version())
        );
        Update update = new Update()
                .set(VERSION, appServiceDTO.version() + 1)
                .set(RESOURCES, ResourceMapper.toEntityListForUpdate(appServiceDTO.resources()));

        FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions()
                .returnNew(true);

        AppService result = mongoTemplate.findAndModify(query, update, findAndModifyOptions, AppService.class);

        if (result == null) {
            throw new ConcurrentModificationException(CONCURRENT_MODIFICATION.getMessage());
        }

        return AppServiceMapper.toDTO(result);
    }

    @Cacheable(value = CACHE_APP_SERVICE, key = "#id")
    @Override
    public AppServiceDto findById(final UUID id) {
        log.debug("Searching for service with id {}", id);
        return AppServiceMapper.toDTO(serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.NOT_FOUND.getMessage(), SERVICE_ENTITY_NAME, id))));
    }

    @Cacheable(CACHE_APP_SERVICES)
    @Override
    public RestPage<AppServiceDto> findAll(PageRequest pageRequest) {
        return new RestPage<>(serviceRepository.findAll(pageRequest).map(AppServiceMapper::toDTO));
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_APP_SERVICES, allEntries = true),
            @CacheEvict(value = CACHE_APP_SERVICE, key = "#id")
    })
    @Override
    public void delete(UUID id) {
        ensureServiceExists(id);
        serviceRepository.deleteById(id);
    }

    private void ensureServiceExists(UUID id) {
        if (!serviceRepository.existsById(id)) {
            throw new NotFoundException(String.format(ExceptionMessage.NOT_FOUND.getMessage(), SERVICE_ENTITY_NAME, id));
        }
    }
}
