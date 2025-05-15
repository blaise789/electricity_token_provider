package com.vehicle_tracking.services;

import com.vehicle_tracking.dtos.requests.OwnerRequest;
import com.vehicle_tracking.dtos.response.OwnerResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOwnerService {
    void deleteOwner(Long id);

    OwnerResponse updateOwner(Long id, @Valid OwnerRequest ownerRequest);

    Page<OwnerResponse> searchOwners(String nationalId, String email, String phone, Pageable pageable);

    Page<OwnerResponse> getAllOwners(Pageable pageable);

    OwnerResponse registerOwner(@Valid OwnerRequest ownerRequest);

    OwnerResponse getOwnerById(Long id);
}
