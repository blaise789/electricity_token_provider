package com.vehicle_tracking.vehicle_tracking.dtos.response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class OwnerResponseDTO {
    // Getters and Setters
    private Long id;
    private String names;
    private String nationalId;
    private String phoneNumber;
    private String address;
    private List<PlateNumberResponse> plateNumbers;

}