package com.vehicle_tracking.services;

import com.vehicle_tracking.dtos.requests.CreateTransferDTO;
import com.vehicle_tracking.dtos.response.TransferResponseDTO;
import com.vehicle_tracking.models.VehicleTransfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITransferService {
    void createTransfer( CreateTransferDTO dto);

    Page<TransferResponseDTO> getVehicleHistory(String chassisNumber, String plateNumber, Pageable pageable);

}
