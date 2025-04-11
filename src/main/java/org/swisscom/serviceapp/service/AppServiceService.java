package org.swisscom.serviceapp.service;

import org.swisscom.serviceapp.model.AppService;
import org.swisscom.serviceapp.model.dto.AppServiceDTO;

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
