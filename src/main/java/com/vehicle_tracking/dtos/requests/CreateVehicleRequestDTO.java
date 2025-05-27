package com.vehicle_tracking.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateVehicleRequestDTO {
//    owner
    private Long ownerId;
    private String chassisNumber;
    private String modelName;
    private LocalDateTime manufactureDate;
    private String manufacturer;
    private double price;
    private Long plateNumberId;
}
