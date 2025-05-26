package com.vehicle_tracking.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerRequest {
    private String names;
    private String nationalId;
    private String phoneNumber;
    private String address;
    private String email;



}