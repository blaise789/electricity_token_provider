package com.vehicle_tracking.controllers;

import com.vehicle_tracking.dtos.requests.VehicleOwnershipHistoryDTO;
import com.vehicle_tracking.dtos.response.ApiResponseDTO;
import com.vehicle_tracking.services.impl.VehicleOwnershipHistoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history/vehicles")
// Changed base path for better grouping
@RequiredArgsConstructor
public class VehicleOwnershipHistoryController {

    private final  VehicleOwnershipHistoryService vehicleOwnershipHistoryService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getVehicleOwnershipHistory(
            @RequestParam(name = "chassisNumber", required = false) String chassisNumber,
            @RequestParam(name = "plateNumber", required = false) String plateNumber) {

        if ((chassisNumber == null || chassisNumber.trim().isEmpty()) && (plateNumber == null || plateNumber.trim().isEmpty())) {
            // Using a simple string response, replace with ApiResponse if available
            return ResponseEntity.badRequest().body("Either chassisNumber or plateNumber must be provided");
        }

        List<VehicleOwnershipHistoryDTO> history;
        String identifier;
        boolean isChassis;

        if (chassisNumber != null && !chassisNumber.trim().isEmpty()) {
            identifier = chassisNumber;
            isChassis = true;
        } else {
            identifier = plateNumber;
            isChassis = false;
        }

        history = vehicleOwnershipHistoryService.getVehicleOwnershipHistory(identifier, isChassis);

        if (history.isEmpty()) {
            // Using a simple string response, replace with ApiResponse if available
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found or no history available for the given identifier.");
        }

        // Using a simple list response, replace with ApiResponse if available
         return ResponseEntity.ok(new ApiResponseDTO(true, "Vehicle ownership history retrieved successfully", history));
    }
}