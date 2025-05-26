package com.vehicle_tracking.dtos.response;

import com.vehicle_tracking.models.Owner;

import java.util.Date;

public class VehicleTransferResponseDTO{
    double transferAmount;
    Date transferDate;
    Owner previousOwner;
    Owner newOwner;


}
