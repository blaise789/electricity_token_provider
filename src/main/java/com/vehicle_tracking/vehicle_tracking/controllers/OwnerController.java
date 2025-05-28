package com.vehicle_tracking.vehicle_tracking.controllers;
import com.vehicle_tracking.vehicle_tracking.dtos.requests.OwnerRequest;
import com.vehicle_tracking.vehicle_tracking.dtos.response.ApiResponseDTO;
import com.vehicle_tracking.vehicle_tracking.dtos.response.OwnerResponseDTO;
import com.vehicle_tracking.vehicle_tracking.models.Owner;
import com.vehicle_tracking.vehicle_tracking.services.IOwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor

public class OwnerController {

    private final IOwnerService ownerService;




    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO>  createOwner(@Valid @RequestBody OwnerRequest dto) {

            OwnerResponseDTO response = ownerService.createOwner(dto);
            return ResponseEntity.ok().body(ApiResponseDTO.success("owner created successfully", response));
 }


    @GetMapping("/search")
    public ResponseEntity<Page<Owner>> searchOwners(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(required = false) String nationalId,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            Pageable pageable) {
        return ResponseEntity.ok(ownerService.searchOwners( pageable,nationalId, email, phone));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerResponseDTO> updateOwner(@PathVariable Long id, @Valid @RequestBody OwnerRequest ownerRequest) {
        return ResponseEntity.ok(ownerService.updateOwner(id, ownerRequest));
    }
//    @PutMapping("/{id}/vehicles/{id}")
////    only for admins
//    public  assignVehicleToOwner( @Valid  AssignVehicleDto){
//
////        return vehicle assigned
//
//    }



}