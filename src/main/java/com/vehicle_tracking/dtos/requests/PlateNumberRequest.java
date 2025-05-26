package com.vehicle_tracking.dtos.requests;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PlateNumberRequest {
    // Getters and Setters
    private String plateNumber;
    private LocalDate issuedDate;
    private LocalDate expiryDate;

}