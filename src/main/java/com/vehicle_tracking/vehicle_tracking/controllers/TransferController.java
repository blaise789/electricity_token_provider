package com.vehicle_tracking.vehicle_tracking.controllers;

import com.vehicle_tracking.vehicle_tracking.annotations.Auditable;
import com.vehicle_tracking.vehicle_tracking.dtos.requests.CreateTransferDTO;
import com.vehicle_tracking.vehicle_tracking.dtos.response.ApiResponseDTO;
import com.vehicle_tracking.vehicle_tracking.exceptions.BadRequestException;
import com.vehicle_tracking.vehicle_tracking.services.ITransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @Auditable(action = "TRANSFER_VEHICLE")
    ResponseEntity<ApiResponseDTO> createTransfer(@Valid @RequestBody CreateTransferDTO dto)
            throws BadRequestException {
        transferService.createTransfer(dto);
        return ResponseEntity
                .ok(ApiResponseDTO.success("transfer created successfully"));
    }

    // get all transfer by search vehicle chassis number or plateNumber by admin


}
