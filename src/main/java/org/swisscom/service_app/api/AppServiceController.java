package org.swisscom.service_app.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.swisscom.service_app.model.AppService;
import org.swisscom.service_app.model.dto.AppServiceDTO;
import org.swisscom.service_app.service.AppServiceService;

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
    public ResponseEntity<AppServiceDTO> save(@RequestBody AppServiceDTO appServiceDTO) {
        return ResponseEntity.ok(service.save(appServiceDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppServiceDTO> update(@PathVariable UUID id, @RequestBody AppServiceDTO appServiceDTO) {
        return ResponseEntity.ok(service.update(id, appServiceDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppServiceDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
