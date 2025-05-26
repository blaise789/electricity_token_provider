package com.vehicle_tracking.services;

import com.vehicle_tracking.dtos.requests.CreateOwnerDTO;
import com.vehicle_tracking.dtos.requests.OwnerRequest;
import com.vehicle_tracking.dtos.response.OwnerResponseDTO;
import com.vehicle_tracking.models.Owner;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOwnerService {
    OwnerResponseDTO    createOwner(@Valid OwnerRequest ownerRequest);
    void deleteOwner(Long id);

    OwnerResponseDTO updateOwner(Long id, @Valid OwnerRequest ownerRequest);


    Page<Owner> searchOwners(Pageable pageable,String nationalId,String email,String phoneNumber);

   Owner getOwnerById(Long id);
}
