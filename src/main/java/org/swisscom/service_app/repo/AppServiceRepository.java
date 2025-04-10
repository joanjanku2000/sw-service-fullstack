package org.swisscom.service_app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.swisscom.service_app.model.AppService;

import java.util.UUID;

/**
 * Service interface repo providing
 * basic CRUD operations for {@link AppService}
 */
public interface AppServiceRepository extends MongoRepository<AppService, UUID> {
}
