package com.vehicle_tracking.controllers;
import com.vehicle_tracking.dtos.requests.OwnerRequest;
import com.vehicle_tracking.dtos.response.OwnerResponse;
import com.vehicle_tracking.services.IOwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor

public class OwnerController {


    private final IOwnerService ownerService;

    @PostMapping
    public ResponseEntity<OwnerResponse> registerOwner(@Valid @RequestBody OwnerRequest ownerRequest) {
        return ResponseEntity.ok(ownerService.registerOwner(ownerRequest));
    }

    @GetMapping
    public ResponseEntity<Page<OwnerResponse>> getAllOwners(Pageable pageable) {
        return ResponseEntity.ok(ownerService.getAllOwners(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponse> getOwnerById(@PathVariable Long id) {
        return ResponseEntity.ok(ownerService.getOwnerById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OwnerResponse>> searchOwners(
            @RequestParam(required = false) String nationalId,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            Pageable pageable) {
        return ResponseEntity.ok(ownerService.searchOwners(nationalId, email, phone, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerResponse> updateOwner(@PathVariable Long id, @Valid @RequestBody OwnerRequest ownerRequest) {
        return ResponseEntity.ok(ownerService.updateOwner(id, ownerRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.ok().build();
    }
}