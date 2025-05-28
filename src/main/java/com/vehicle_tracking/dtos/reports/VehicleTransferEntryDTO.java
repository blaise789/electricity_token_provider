package com.vehicle_tracking.dtos.reports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTransferEntryDTO {
    private LocalDateTime transferDate;
    private String vehicleChassisNumber;
    private String vehicleModel;
    private String previousOwnerName;
    private String previousOwnerNationalId;
    private String newOwnerName;
    private String newOwnerNationalId;
    private String previousPlateNumber;
    private String newPlateNumber;
    private double transferAmount;
}