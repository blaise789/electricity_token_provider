package com.vehicle_tracking.dtos.response;

import com.vehicle_tracking.models.Owner;
import com.vehicle_tracking.models.Plate;

import java.math.BigDecimal;
import java.util.Date;

public class TransferHistoryDTO {
    private Long id;
private Owner previousOwner;
private Owner currentOwner;
private Plate previousPlateNumber;
private Plate currentPlateNumber;
private BigDecimal transferAmount;
private Date transferDate;
}
