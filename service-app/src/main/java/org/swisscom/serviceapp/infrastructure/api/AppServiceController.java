package org.swisscom.serviceapp.infrastructure.api;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.swisscom.serviceapp.domain.model.AppService;
import org.swisscom.serviceapp.infrastructure.dto.AppServiceDto;
import org.swisscom.serviceapp.infrastructure.dto.PageInput;
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
    public ResponseEntity<AppServiceDto> save(@RequestBody @Valid org.swisscom.serviceapp.infrastructure.dto.AppServiceDto appServiceDTO) {
        return ResponseEntity.ok(service.save(appServiceDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppServiceDto> update(@PathVariable UUID id, @RequestBody @Valid org.swisscom.serviceapp.infrastructure.dto.AppServiceDto appServiceDTO) {
        return ResponseEntity.ok(service.update(id, appServiceDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppServiceDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/all")
    public ResponseEntity<Page<AppServiceDto>> findAll(@RequestBody PageInput pageInput) {
        return ResponseEntity.ok(service.findAll(PageRequest.of(pageInput.getOffset(),pageInput.getPageSize(), Sort.by(pageInput.getSortDirectionDefault(), pageInput.getSortByDefault()))));
    }
}
