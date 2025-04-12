package org.swisscom.serviceapp.infrastructure.service;

import org.swisscom.serviceapp.domain.model.AppService;
import org.swisscom.serviceapp.infrastructure.dto.AppServiceDto;

import java.util.UUID;

/**
 * Service interface providing
 * basic CRUD operations for {@link AppService}
 */
public interface AppServiceService {
    AppServiceDto save(AppServiceDto appServiceDTO);
    AppServiceDto update(UUID id, AppServiceDto appServiceDTO);
    AppServiceDto findById(UUID id);
}
