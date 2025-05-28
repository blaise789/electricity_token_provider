package com.vehicle_tracking.vehicle_tracking.services.impl;

import com.vehicle_tracking.vehicle_tracking.dtos.requests.CreateVehicleRequestDTO;
import com.vehicle_tracking.vehicle_tracking.dtos.response.VehicleResponseDTO;
import com.vehicle_tracking.vehicle_tracking.enums.EPlateStatus;
import com.vehicle_tracking.vehicle_tracking.exceptions.BadRequestException;
import com.vehicle_tracking.vehicle_tracking.exceptions.ResourceNotFoundException;
import com.vehicle_tracking.vehicle_tracking.mappers.VehicleMapper;
import com.vehicle_tracking.vehicle_tracking.models.Owner;
import com.vehicle_tracking.vehicle_tracking.models.Plate;
import com.vehicle_tracking.vehicle_tracking.models.Vehicle;
import com.vehicle_tracking.vehicle_tracking.repositories.OwnerRepository;
import com.vehicle_tracking.vehicle_tracking.repositories.PlateRepository;
import com.vehicle_tracking.vehicle_tracking.repositories.VehicleRepository;
import com.vehicle_tracking.vehicle_tracking.services.IVehicleService;
import com.vehicle_tracking.vehicle_tracking.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements IVehicleService {
    private final VehicleRepository vehicleRepository;
    private final OwnerRepository ownerRepository;
    private final PlateRepository plateNumberRepository;
    @Override
    public VehicleResponseDTO registerVehicle(CreateVehicleRequestDTO dto) {
        Owner owner = ownerRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found with ID: " + dto.getOwnerId()));
        Plate plateNumber = plateNumberRepository.findById(dto.getPlateNumberId())
                .orElseThrow(() -> new RuntimeException("Plate number not found with ID: " + dto.getPlateNumberId()));

        if(!plateNumber.getStatus().equals(EPlateStatus.AVAILABLE)){
            throw new BadRequestException("Plate number is already in use");
        }

        if(!plateNumber.getOwner().getId().equals(owner.getId())){
            throw  new BadRequestException("Plate number does not belong to owner");
        }
//        check if Vehicle already exists

        Optional<Vehicle> vehicleExists=vehicleRepository.findByChassisNumber(dto.getChassisNumber());
        if(vehicleExists.isPresent()) {
            throw new BadRequestException("Vehicle already exists");
        }
        Vehicle vehicleEntity = VehicleMapper.toEntity(dto);
        vehicleEntity.setOwner(owner);
        vehicleEntity.setPlate(plateNumber);
        Vehicle newVehicle=vehicleRepository.save(vehicleEntity);
        return  Mapper.getMapper().map(newVehicle, VehicleResponseDTO.class);

    }

    @Override
    public VehicleResponseDTO displayVehicleDetails(Long id) {
        Vehicle vehicle=vehicleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(" vehicle not found","id",id));
        return VehicleMapper.toResponseDTO(vehicle);
    }

    @Override
    public VehicleResponseDTO searchVehicle(String searchKey) {
        Vehicle vehicle = vehicleRepository.findVehicleByPlate_PlateNumber(searchKey)
                .or(() -> vehicleRepository.findByChassisNumber(searchKey))
                .or(() -> vehicleRepository.findVehicleByOwner_NationalId(searchKey))
                .orElseThrow(() -> new RuntimeException("Vehicle not found with keyword: " + searchKey));
        return Mapper.getMapper().map(vehicle, VehicleResponseDTO.class) ;


    }

    @Override
    public Page<VehicleResponseDTO> getAllVehicles(Pageable pageable) {
            Page<Vehicle> page = vehicleRepository.findAll(pageable);
            log.info("{}",page.getContent());
            return page.map(vehicle -> Mapper.getMapper().map(vehicle, VehicleResponseDTO.class));
    }


}
