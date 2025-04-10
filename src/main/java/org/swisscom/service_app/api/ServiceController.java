package org.swisscom.service_app.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.swisscom.service_app.api.dto.ServiceDTO;

/**
 * Rest Controller Class for managing CRUD operations
 * on {@link ServiceDTO} todo link the entity
 */
@RestController
@RequestMapping("/v1/service")
public class ServiceController {

    @PostMapping
    public ResponseEntity<ServiceDTO> save(ServiceDTO serviceDTO) {
        // TODO implement logic
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceDTO> update(@PathVariable String id, ServiceDTO serviceDTO) {
        // TODO implement logic
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> findById(String id) {
        // TODO implement logic
        return ResponseEntity.ok().build();
    }
}
