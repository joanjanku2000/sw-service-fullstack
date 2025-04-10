package org.swisscom.service_app.service;

import org.swisscom.service_app.model.AppService;
import org.swisscom.service_app.model.dto.AppServiceDTO;

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
