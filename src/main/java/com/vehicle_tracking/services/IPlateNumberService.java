package com.vehicle_tracking.services;

import com.vehicle_tracking.dtos.requests.PlateNumberRequest;
import com.vehicle_tracking.dtos.response.PlateNumberResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface IPlateNumberService {
    PlateNumberResponse addPlateNumber(Long ownerId, @Valid PlateNumberRequest plateNumberRequest);

    List<PlateNumberResponse> getPlateNumbersByOwner(Long ownerId);

    PlateNumberResponse updatePlateStatus(Long plateId, String status);
}
