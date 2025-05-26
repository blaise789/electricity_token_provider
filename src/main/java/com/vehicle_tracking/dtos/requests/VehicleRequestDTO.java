package com.vehicle_tracking.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class VehicleRequestDTO {
    private String chassisNumber;
    private String manufacturer;
    private int manufactureYear;
    private BigDecimal price;
    private String modelName;
    private Long ownerId;
    private Long plateNumberId;
}
