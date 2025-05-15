package com.vehicle_tracking.services.impl;

import com.vehicle_tracking.dtos.requests.PlateNumberRequest;
import com.vehicle_tracking.dtos.response.PlateNumberResponse;
import com.vehicle_tracking.services.IPlateNumberService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PlateNumberServiceImpl implements IPlateNumberService {
    @Override
    public PlateNumberResponse addPlateNumber(Long ownerId, PlateNumberRequest plateNumberRequest) {
        return null;
    }

    @Override
    public List<PlateNumberResponse> getPlateNumbersByOwner(Long ownerId) {
        return List.of();
    }

    @Override
    public PlateNumberResponse updatePlateStatus(Long plateId, String status) {
        return null;
    }
}
