package com.vehicle_tracking.services;

import com.vehicle_tracking.dtos.requests.PlateNumberRequest;
import com.vehicle_tracking.dtos.response.PlateNumberResponse;
import com.vehicle_tracking.enums.EPlateStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPlateNumberService {
    PlateNumberResponse addPlateNumber(Long ownerId, @Valid PlateNumberRequest plateNumberRequest);

    Page<PlateNumberResponse> getPlateNumbersByOwner(Long ownerId, Pageable pageable);

    PlateNumberResponse updatePlateStatus(Long plateId, EPlateStatus status);
}
