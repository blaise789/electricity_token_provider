package com.vehicle_tracking.services;

import com.vehicle_tracking.dtos.requests.CreateVehicleRequestDTO;
import com.vehicle_tracking.dtos.response.VehicleResponseDTO;
import com.vehicle_tracking.models.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVehicleService {
    public VehicleResponseDTO registerVehicle(CreateVehicleRequestDTO vehicle);
    public VehicleResponseDTO displayVehicleDetails(Long id);
  public VehicleResponseDTO searchVehicle(String searchKey);
  public List<Page<VehicleResponseDTO>> getAllVehicles(Pageable pageable);

}
