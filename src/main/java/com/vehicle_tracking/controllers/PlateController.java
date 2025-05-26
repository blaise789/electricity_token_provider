package com.vehicle_tracking.controllers;

import com.vehicle_tracking.dtos.requests.PlateNumberRequest;
import com.vehicle_tracking.dtos.response.PlateNumberResponse;
import com.vehicle_tracking.enums.EPlateStatus;
import com.vehicle_tracking.services.IPlateNumberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class PlateController {


    private  final IPlateNumberService plateNumberService;


    @PostMapping("/api/owners/{ownerId}/plates")
    public ResponseEntity<PlateNumberResponse> assigningPlateNumberToOwner(
            @PathVariable Long ownerId,
            @Valid @RequestBody PlateNumberRequest plateNumberRequest) {
        return ResponseEntity.ok(plateNumberService.addPlateNumber(ownerId, plateNumberRequest));
    }


    @GetMapping("/api/owners/{ownerId}/plates")
    public ResponseEntity<List<PlateNumberResponse>> getPlateNumbersByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(plateNumberService.getPlateNumbersByOwner(ownerId));
    }

    @PutMapping("/api/plates/{plateId}/status")
    public ResponseEntity<PlateNumberResponse> updatePlateStatus(
            @PathVariable Long plateId,
            @RequestParam EPlateStatus status) {
        return ResponseEntity.ok(plateNumberService.updatePlateStatus(plateId, status));
    }
}