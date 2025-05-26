package com.vehicle_tracking.services;

import com.vehicle_tracking.dtos.requests.CreateTransferDTO;
import com.vehicle_tracking.dtos.response.VehicleTransferResponseDTO;
import com.vehicle_tracking.models.VehicleTransfer;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITransferService {
    void createTransfer( CreateTransferDTO dto);

    Page<VehicleTransfer> getVehicleHistory(String chassisNumber, String plateNumber, Pageable pageable);

}
