package com.vehicle_tracking.services.impl;

import com.vehicle_tracking.dtos.requests.PlateNumberRequest;
import com.vehicle_tracking.dtos.response.PlateNumberResponse;
import com.vehicle_tracking.enums.EPlateStatus;
import com.vehicle_tracking.exceptions.ResourceNotFoundException;
import com.vehicle_tracking.models.Owner;
import com.vehicle_tracking.models.Plate;
import com.vehicle_tracking.repositories.PlateRepository;
import com.vehicle_tracking.services.IOwnerService;
import com.vehicle_tracking.services.IPlateNumberService;
import com.vehicle_tracking.utils.Mapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PlateNumberServiceImpl implements IPlateNumberService {
    private final PlateRepository plateNumberRepository;
    private final IOwnerService ownerService;


    @Override
    public PlateNumberResponse addPlateNumber(Long ownerId, PlateNumberRequest plateNumberRequest) {
        Owner owner=ownerService.getOwnerById(ownerId);
        Plate plateNumber = new Plate();
        plateNumber.setOwner(owner);
        plateNumber.setPlateNumber(plateNumberRequest.getPlateNumber());
        plateNumber.setIssuedDate(plateNumberRequest.getIssuedDate());
        plateNumber.setExpirationDate(plateNumberRequest.getExpiryDate());
       Plate newPlateNumber= plateNumberRepository.save(plateNumber);
       PlateNumberResponse plateNumberResponse = new PlateNumberResponse();
       plateNumber.setPlateNumber(newPlateNumber.getPlateNumber());
       return plateNumberResponse;

    }

    @Override
    public List<PlateNumberResponse> getPlateNumbersByOwner(Long ownerId) {
        return List.of();
    }

    @Override
    public PlateNumberResponse updatePlateStatus(Long plateId, EPlateStatus status) {
Plate plateExists=plateNumberRepository.findById(plateId).orElseThrow(()->new ResourceNotFoundException("Plate not found","id",plateId));
plateExists.setStatus(status);
Plate updatedPlate=plateNumberRepository.save(plateExists);
return Mapper.getMapper().map(updatedPlate, PlateNumberResponse.class);
}
}