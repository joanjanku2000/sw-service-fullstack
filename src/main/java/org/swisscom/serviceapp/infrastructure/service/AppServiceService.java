package org.swisscom.serviceapp.infrastructure.service;

import org.swisscom.serviceapp.domain.model.AppService;
import org.swisscom.serviceapp.infrastructure.dto.AppServiceDTO;

import java.util.UUID;

/**
 * Service interface providing
 * basic CRUD operations for {@link AppService}
 */
public interface AppServiceService {
    AppServiceDTO save(AppServiceDTO appServiceDTO);
    AppServiceDTO update(UUID id, AppServiceDTO appServiceDTO);
    AppServiceDTO findById(UUID id);
}
