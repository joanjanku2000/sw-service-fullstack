package org.swisscom.serviceapp.domain.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.swisscom.serviceapp.domain.model.AppService;

import java.util.UUID;

/**
 * Service interface repo providing
 * basic CRUD operations for {@link AppService}
 */
public interface AppServiceRepository extends MongoRepository<AppService, UUID> {
}
