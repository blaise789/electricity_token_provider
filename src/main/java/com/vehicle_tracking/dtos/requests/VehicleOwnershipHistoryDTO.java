package com.vehicle_tracking.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleOwnershipHistoryDTO {
    private String ownerName;
    private String ownerNationalId;
    private String plateNumberUsed;
    private double purchasePrice; // How much this owner bought it for
    private LocalDateTime ownershipStartDate;
    private LocalDateTime ownershipEndDate; // Can be null if it's the current owner and this is the last record
    private String remarks; // e.g., "Initial Registration", "Transferred"
}