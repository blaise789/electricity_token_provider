package com.vehicle_tracking.dtos.response;

import com.vehicle_tracking.enums.EPlateStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PlateNumberResponse {
    private Long id;
    private String plateNumber;
    private Date issuedDate;
    private EPlateStatus available;
}
