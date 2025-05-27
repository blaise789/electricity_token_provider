package com.vehicle_tracking.dtos.response;

import com.vehicle_tracking.models.Owner;
import com.vehicle_tracking.models.Plate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponseDTO {
private String previousOwner;
private String currentOwner;
private LocalDateTime transferDate;
}
