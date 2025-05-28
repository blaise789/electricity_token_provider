package com.vehicle_tracking.dtos.reports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTransferReportDTO {
    private List<VehicleTransferEntryDTO> transfers;
    private long totalTransfers;
    private double totalTransferAmount;
    private String periodDescription; // e.g., "From YYYY-MM-DD to YYYY-MM-DD"
}