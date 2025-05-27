package com.vehicle_tracking.controllers;

import com.vehicle_tracking.dtos.requests.PlateNumberRequest;
import com.vehicle_tracking.dtos.response.PlateNumberResponse;
import com.vehicle_tracking.enums.EPlateStatus;
import com.vehicle_tracking.services.IPlateNumberService;
import com.vehicle_tracking.utils.Constants;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class PlateController {


    private final IPlateNumberService plateNumberService;


    @PostMapping("/api/owners/{ownerId}/plates")
    public ResponseEntity<PlateNumberResponse> assigningPlateNumberToOwner(
            @PathVariable Long ownerId,
            @Valid @RequestBody PlateNumberRequest plateNumberRequest) {
        return ResponseEntity.ok(plateNumberService.addPlateNumber(ownerId, plateNumberRequest));
    }


    @GetMapping("/api/owners/{ownerId}/plates")
    public ResponseEntity<Page<PlateNumberResponse>> getPlateNumbersByOwner(@PathVariable Long ownerId, @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return ResponseEntity.ok(plateNumberService.getPlateNumbersByOwner(ownerId,pageable));
    }
//
}