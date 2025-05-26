package com.vehicle_tracking.controllers;

import com.vehicle_tracking.dtos.requests.CreateTransferDTO;
import com.vehicle_tracking.dtos.response.ApiResponseDTO;
import com.vehicle_tracking.dtos.response.VehicleTransferResponseDTO;
import com.vehicle_tracking.exceptions.BadRequestException;
import com.vehicle_tracking.repositories.VehicleRepository;
import com.vehicle_tracking.services.IOwnerShipService;
import com.vehicle_tracking.services.ITransferService;
import com.vehicle_tracking.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
public class TransferController {
    private final ITransferService transferService;

    @Operation(summary = "Register a new transfer", security = @SecurityRequirement(name = "bearerAuth")

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "transfer registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    ResponseEntity<ApiResponseDTO> createTransfer(@Valid @RequestBody CreateTransferDTO dto)
            throws BadRequestException {
        transferService.createTransfer(dto);
        return ResponseEntity
                .ok(ApiResponseDTO.success("transfer created successfully"));
    }

    // get all transfer by search vehicle chassis number or plateNumber by admin
    @Operation(summary = " search   vehicle by admin ", description = " get all transfer by search    vehicle chassis number  or plateNumber", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/vehicle/history")
    public ResponseEntity<ApiResponseDTO> getVehicleHistory(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @RequestParam(required = false) String chassisNumber,
            @RequestParam(required = false) String plateNumber) {
        Pageable pageable = Pageable.ofSize(limit).withPage(page);

        return ResponseEntity
                .ok(ApiResponseDTO.success(" transfers  fetched successfully",
                        transferService.getVehicleHistory(chassisNumber, plateNumber, pageable)));
    }


}
