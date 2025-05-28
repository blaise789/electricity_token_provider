package com.vehicle_tracking.vehicle_tracking.services;

import com.vehicle_tracking.vehicle_tracking.dtos.CustomPageDTO;
import com.vehicle_tracking.vehicle_tracking.dtos.requests.PlateNumberRequest;
import com.vehicle_tracking.vehicle_tracking.dtos.response.PlateNumberResponse;
import com.vehicle_tracking.vehicle_tracking.enums.EPlateStatus;
import jakarta.validation.Valid;

public interface IPlateNumberService {
    PlateNumberResponse addPlateNumber(Long ownerId, @Valid PlateNumberRequest plateNumberRequest);

    CustomPageDTO<PlateNumberResponse> getPlateNumbersByOwner(Long ownerId, int page, int limit  ) throws Exception;

    PlateNumberResponse updatePlateStatus(Long plateId, EPlateStatus status);
}
