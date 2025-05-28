package com.vehicle_tracking.vehicle_tracking.services.impl;

import com.vehicle_tracking.vehicle_tracking.dtos.CustomPageDTO;
import com.vehicle_tracking.vehicle_tracking.dtos.requests.PlateNumberRequest;
import com.vehicle_tracking.vehicle_tracking.dtos.response.PlateNumberResponse;
import com.vehicle_tracking.vehicle_tracking.enums.EPlateStatus;
import com.vehicle_tracking.vehicle_tracking.exceptions.ResourceNotFoundException;
import com.vehicle_tracking.vehicle_tracking.models.Owner;
import com.vehicle_tracking.vehicle_tracking.models.Plate;
import com.vehicle_tracking.vehicle_tracking.repositories.PlateRepository;
import com.vehicle_tracking.vehicle_tracking.services.IOwnerService;
import com.vehicle_tracking.vehicle_tracking.services.IPlateNumberService;
import com.vehicle_tracking.vehicle_tracking.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlateNumberServiceImpl implements IPlateNumberService {
    private final PlateRepository plateNumberRepository;
    private final IOwnerService ownerService;
    private static final String OWNER_PLATES_CACHE_PREFIX = "ownerPlates:";


    @Override
    public PlateNumberResponse addPlateNumber(Long ownerId, PlateNumberRequest plateNumberRequest) {
        Owner owner=ownerService.getOwnerById(ownerId);
        Plate plateNumber = new Plate();
        plateNumber.setOwner(owner);
        plateNumber.setPlateNumber(plateNumberRequest.getPlateNumber());
        plateNumber.setIssuedDate(plateNumberRequest.getIssuedDate());
        plateNumber.setExpirationDate(plateNumberRequest.getExpiryDate());
       Plate newPlateNumber= plateNumberRepository.save(plateNumber);
       log.info("{},{}",newPlateNumber.getIssuedDate(),newPlateNumber.getStatus());
       return Mapper.getMapper().map(newPlateNumber, PlateNumberResponse.class);

    }

    @Override
//    @Cacheable(cacheNames = "owner  " , key = "#ownerId + '_' + #page + '_' + #size")
    public CustomPageDTO<PlateNumberResponse> getPlateNumbersByOwner(Long ownerId, int page, int limit) throws Exception {

        Owner owner = ownerService.getOwnerById(ownerId);

        Pageable sortedPageable = PageRequest.of(
                page,
                limit,
                Sort.by("issuedDate").descending()
        );
        Page<Plate> owner_plates = plateNumberRepository.findByOwner(owner, sortedPageable);
        Page<PlateNumberResponse> plateNumberResponses= owner_plates.map((plate) ->
                Mapper.getMapper().map(plate, PlateNumberResponse.class)
        );
        return new CustomPageDTO<>(plateNumberResponses);

    }

    @Override
    public PlateNumberResponse updatePlateStatus(Long plateId, EPlateStatus status) {
Plate plateExists=plateNumberRepository.findById(plateId).orElseThrow(()->new ResourceNotFoundException("Plate not found","id",plateId));
plateExists.setStatus(status);
Plate updatedPlate=plateNumberRepository.save(plateExists);
return Mapper.getMapper().map(updatedPlate, PlateNumberResponse.class);
}
}