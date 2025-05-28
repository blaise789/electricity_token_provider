package com.vehicle_tracking.vehicle_tracking.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponseDTO {
private String previousOwner;
private String currentOwner;
private LocalDateTime transferDate;
}
