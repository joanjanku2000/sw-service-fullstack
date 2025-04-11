package org.swisscom.serviceapp.infrastructure.service;

import org.swisscom.serviceapp.domain.model.AppService;

import java.util.UUID;

/**
 * Service interface providing
 * basic CRUD operations for {@link AppService}
 */
public interface AppServiceService {
    org.swisscom.serviceapp.infrastructure.dto.AppServiceDto save(org.swisscom.serviceapp.infrastructure.dto.AppServiceDto appServiceDTO);
    org.swisscom.serviceapp.infrastructure.dto.AppServiceDto update(UUID id, org.swisscom.serviceapp.infrastructure.dto.AppServiceDto appServiceDTO);
    org.swisscom.serviceapp.infrastructure.dto.AppServiceDto findById(UUID id);
}
