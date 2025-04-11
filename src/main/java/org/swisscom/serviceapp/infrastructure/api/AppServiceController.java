package org.swisscom.serviceapp.infrastructure.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.swisscom.serviceapp.domain.model.AppService;
import org.swisscom.serviceapp.infrastructure.dto.AppServiceDTO;
import org.swisscom.serviceapp.infrastructure.service.AppServiceService;

import java.util.UUID;

/**
 * Rest Controller Class for managing CRUD operations
 * on {@link AppService}
 */
@RestController
@RequestMapping("/v1/service")
public class AppServiceController {

    private final AppServiceService service;

    public AppServiceController(AppServiceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AppServiceDTO> save(@RequestBody @Valid AppServiceDTO appServiceDTO) {
        return ResponseEntity.ok(service.save(appServiceDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppServiceDTO> update(@PathVariable UUID id, @RequestBody @Valid AppServiceDTO appServiceDTO) {
        return ResponseEntity.ok(service.update(id, appServiceDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppServiceDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
