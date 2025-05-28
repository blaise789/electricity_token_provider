package com.vehicle_tracking.vehicle_tracking.services;

import com.vehicle_tracking.vehicle_tracking.dtos.requests.CreateVehicleRequestDTO;
import com.vehicle_tracking.vehicle_tracking.dtos.response.VehicleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVehicleService {
    public VehicleResponseDTO registerVehicle(CreateVehicleRequestDTO vehicle);
    public VehicleResponseDTO displayVehicleDetails(Long id);
  public VehicleResponseDTO searchVehicle(String searchKey);
  public Page<VehicleResponseDTO> getAllVehicles(Pageable pageable);

}
