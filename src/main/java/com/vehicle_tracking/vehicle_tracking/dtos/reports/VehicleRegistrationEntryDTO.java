package com.vehicle_tracking.vehicle_tracking.dtos.reports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRegistrationEntryDTO {
    private String chassisNumber;
    private String manufacturer;
    private Integer manufactureYear;
    private String modelName;
    private double initialPrice;
    private LocalDateTime registrationDate;
    private String ownerName;
    private String ownerNationalId;
    private String plateNumber;
}