package com.vehicle_tracking.dtos.response;

import com.vehicle_tracking.models.Owner;
import com.vehicle_tracking.models.Plate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleResponseDTO {
    private Long id;
    private String chassisNumber;
    private String manufacturer;
    private int manufactureYear;
    private double price;
    private String modelName;
    private Owner currentOwner;
    private Plate currentPlateNumber;

}
