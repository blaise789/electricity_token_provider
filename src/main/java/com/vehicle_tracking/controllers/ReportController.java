package com.vehicle_tracking.controllers; // Or com.vehicle_tracking.controllers.reports

import com.vehicle_tracking.dtos.reports.PlateStatusCountDTO;
import com.vehicle_tracking.dtos.reports.VehicleRegistrationEntryDTO;
import com.vehicle_tracking.dtos.reports.VehicleTransferReportDTO;
import com.vehicle_tracking.dtos.response.ApiResponseDTO;
import com.vehicle_tracking.services.impl.ReportService;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@PreAuthorize("hasRole('ADMIN')") // Apply to all report endpoints
@RequiredArgsConstructor
public class ReportController {


    private final ReportService reportService;

    @GetMapping("/vehicle-registrations")
    public ResponseEntity<?> getVehicleRegistrationReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) String modelName) {

        List<VehicleRegistrationEntryDTO> reportData = reportService.generateVehicleRegistrationReport(
                startDate, endDate, manufacturer, modelName);
         return ResponseEntity.ok(new ApiResponseDTO(true, "Vehicle registration report generated", reportData));
    }

    @GetMapping("/vehicle-transfers")
    public ResponseEntity<?> getVehicleTransferReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        VehicleTransferReportDTO reportData = reportService.generateVehicleTransferReport(startDate, endDate);
          return ResponseEntity.ok(new ApiResponseDTO(true, "Vehicle transfer report generated", reportData));
    }

    @GetMapping("/plate-status-counts")
    public ResponseEntity<?> getPlateStatusCountsReport() {
        List<PlateStatusCountDTO> reportData = reportService.generatePlatesByStatusReport();
          return ResponseEntity.ok(new ApiResponseDTO(true, "Plate status counts report generated", reportData));
    }
}