package com.vehicle_tracking.vehicle_tracking.mappers;

import com.vehicle_tracking.vehicle_tracking.dtos.requests.CreateVehicleRequestDTO;
import com.vehicle_tracking.vehicle_tracking.dtos.response.VehicleResponseDTO;
import com.vehicle_tracking.vehicle_tracking.models.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {
    public static Vehicle toEntity(CreateVehicleRequestDTO dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setChassisNumber( dto.getChassisNumber());
        vehicle.setManufacturer(dto.getManufacturer());
        vehicle.setManufactureYear(dto.getManufactureDate().getYear());
        vehicle.setPrice(dto.getPrice());
        vehicle.setModelName(dto.getModelName());
        return vehicle;
    }
    public static VehicleResponseDTO toResponseDTO(Vehicle vehicle) {
        VehicleResponseDTO vehicleResponseDTO = new VehicleResponseDTO();
        vehicleResponseDTO.setChassisNumber(vehicle.getChassisNumber());
        vehicleResponseDTO.setManufacturer(vehicle.getManufacturer());
        vehicleResponseDTO.setManufactureYear(vehicle.getManufactureYear());
        vehicleResponseDTO.setPrice(vehicle.getPrice());
        vehicleResponseDTO.setModelName(vehicle.getModelName());
        return vehicleResponseDTO;
    }


}
