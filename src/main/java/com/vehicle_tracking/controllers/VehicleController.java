package com.vehicle_tracking.controllers;

import com.vehicle_tracking.dtos.requests.CreateVehicleRequestDTO;
import com.vehicle_tracking.dtos.response.ApiResponseDTO;
import com.vehicle_tracking.dtos.response.ApiResponseDTO;
import com.vehicle_tracking.dtos.response.VehicleResponseDTO;
import com.vehicle_tracking.models.Vehicle;
import com.vehicle_tracking.security.UserPrincipal;
import com.vehicle_tracking.services.IVehicleService;
import com.vehicle_tracking.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {
    private final IVehicleService vehicleService;

@PostMapping("")
public ResponseEntity<ApiResponseDTO> createVehicle( @Valid @RequestBody  CreateVehicleRequestDTO vehicleDto) {
//
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        log.info("User ID: {}, Email: {}",
                userDetails.getId(),
                userDetails.getEmail());
    }
    log.info("Creating vehicle: {}", vehicleDto);
    VehicleResponseDTO vehicleResponse= vehicleService.registerVehicle(vehicleDto);
    return  ResponseEntity.ok( ApiResponseDTO.success("vehicle response dto",vehicleResponse));

}

    //    search   vehicle by admin
    @Operation(
            summary = " search   vehicle by admin ",
            description = " search   vehicle by admin",
            security = @SecurityRequirement(name = "bearerAuth")
    )
@GetMapping("/search")
public ResponseEntity<ApiResponseDTO> searchVehicle(@RequestParam String searchKey) {
    try{
        VehicleResponseDTO response = vehicleService.searchVehicle(searchKey);
        return ResponseEntity.ok(ApiResponseDTO.success("vehicle fetched", response));
    }catch (Exception e){
        return ResponseEntity.ok(ApiResponseDTO.error("Failed to get vehicle",e.getMessage()));
    }

}  //     get all  vehicles by admin
    @Operation(
            summary = "get all  vehicles by admin",
            description = "get all  plates by admin",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " all  plates by admin  fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    private ResponseEntity<ApiResponseDTO> getAllVehiclesByAdmin(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return ResponseEntity.ok(ApiResponseDTO.success("vehicles fetched successfully", vehicleService.getAllVehicles(pageable)));
    }







    
}
