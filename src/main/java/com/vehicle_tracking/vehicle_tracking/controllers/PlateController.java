package com.vehicle_tracking.vehicle_tracking.controllers;

import com.vehicle_tracking.vehicle_tracking.dtos.CustomPageDTO;
import com.vehicle_tracking.vehicle_tracking.dtos.requests.PlateNumberRequest;
import com.vehicle_tracking.vehicle_tracking.dtos.response.PlateNumberResponse;
import com.vehicle_tracking.vehicle_tracking.services.IPlateNumberService;
import com.vehicle_tracking.vehicle_tracking.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
@RequestMapping("/api/plates")
public class PlateController {


    private final IPlateNumberService plateNumberService;


    @PostMapping("/{ownerId}")
    public ResponseEntity<PlateNumberResponse> assigningPlateNumberToOwner(
            @PathVariable Long ownerId,
            @Valid @RequestBody PlateNumberRequest plateNumberRequest) {
        return ResponseEntity.ok(plateNumberService.addPlateNumber(ownerId, plateNumberRequest));
    }


    @GetMapping("/{ownerId}")
    public ResponseEntity<CustomPageDTO<PlateNumberResponse>> getPlateNumbersByOwner(@PathVariable Long ownerId, @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit) throws Exception {
        return ResponseEntity.ok(plateNumberService.getPlateNumbersByOwner(ownerId,page,limit));
    }
//
}