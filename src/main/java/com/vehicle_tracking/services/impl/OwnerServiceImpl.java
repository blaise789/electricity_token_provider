package com.vehicle_tracking.services.impl;

import com.vehicle_tracking.dtos.requests.OwnerRequest;
import com.vehicle_tracking.dtos.response.OwnerResponse;
import com.vehicle_tracking.services.IOwnerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements IOwnerService {
    @Override
    public void deleteOwner(Long id) {

    }

    @Override
    public OwnerResponse updateOwner(Long id, OwnerRequest ownerRequest) {
        return null;
    }

    @Override
    public Page<OwnerResponse> searchOwners(String nationalId, String email, String phone, Pageable pageable) {
        return null;
    }

    @Override
    public Page<OwnerResponse> getAllOwners(Pageable pageable) {
        return null;
    }

    @Override
    public OwnerResponse registerOwner(OwnerRequest ownerRequest) {
        return null;
    }

    @Override
    public OwnerResponse getOwnerById(Long id) {
        return null;
    }
}
