package com.vehicle_tracking.vehicle_tracking.dtos.response;

import com.vehicle_tracking.vehicle_tracking.enums.EPlateStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PlateNumberResponse {
    private Long id;
    private String plateNumber;
    private LocalDate issuedDate;
    private EPlateStatus status;
}
