package com.vehicle_tracking.services.impl; // Or com.vehicle_tracking.services.reports

import com.vehicle_tracking.dtos.reports.PlateStatusCountDTO;
import com.vehicle_tracking.dtos.reports.VehicleRegistrationEntryDTO;
import com.vehicle_tracking.dtos.reports.VehicleTransferEntryDTO;
import com.vehicle_tracking.dtos.reports.VehicleTransferReportDTO;
import com.vehicle_tracking.models.Vehicle;
import com.vehicle_tracking.models.VehicleTransfer;
import com.vehicle_tracking.repositories.PlateRepository;
import com.vehicle_tracking.repositories.VehicleRepository;
import com.vehicle_tracking.repositories.VehicleTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleTransferRepository vehicleTransferRepository;

    @Autowired
    private PlateRepository plateRepository;

    public List<VehicleRegistrationEntryDTO> generateVehicleRegistrationReport(
            LocalDate startDate, LocalDate endDate, String manufacturer, String modelName) {

        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(LocalTime.MAX) : null;

        List<Vehicle> vehicles = vehicleRepository.findVehiclesByRegistrationCriteria(
                startDateTime, endDateTime, manufacturer, modelName);

        return vehicles.stream().map(v -> new VehicleRegistrationEntryDTO(
                v.getChassisNumber(),
                v.getManufacturer(),
                v.getManufactureYear(),
                v.getModelName(),
                v.getPrice(), // Assuming this is initial price
                v.getCreatedDate(), // Assuming this is registration date
                v.getOwner().getNames(),
                v.getOwner().getNationalId(),
                v.getPlate().getPlateNumber()
        )).collect(Collectors.toList());
    }

    public VehicleTransferReportDTO generateVehicleTransferReport(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(LocalTime.MAX) : null;

        List<VehicleTransfer> transfers = vehicleTransferRepository.findTransfersByDateRange(startDateTime, endDateTime);

        List<VehicleTransferEntryDTO> entries = transfers.stream().map(vt -> new VehicleTransferEntryDTO(
                vt.getTransferDate(),
                vt.getVehicle().getChassisNumber(),
                vt.getVehicle().getModelName(),
                vt.getPreviousOwner().getNames(),
                vt.getPreviousOwner().getNationalId(),
                vt.getNewOwner().getNames(),
                vt.getNewOwner().getNationalId(),
                vt.getPreviousPlate().getPlateNumber(),
                vt.getNewPlate().getPlateNumber(),
                vt.getTransferAmount()
        )).collect(Collectors.toList());

        long totalTransfers = entries.size();
        double totalTransferAmount = entries.stream().mapToDouble(VehicleTransferEntryDTO::getTransferAmount).sum();

        String periodDesc = "All Time";
        if (startDate != null && endDate != null) {
            periodDesc = "From " + startDate + " to " + endDate;
        } else if (startDate != null) {
            periodDesc = "From " + startDate + " onwards";
        } else if (endDate != null) {
            periodDesc = "Up to " + endDate;
        }

        return new VehicleTransferReportDTO(entries, totalTransfers, totalTransferAmount, periodDesc);
    }

    public List<PlateStatusCountDTO> generatePlatesByStatusReport() {
        return plateRepository.countPlatesByStatus();
    }
}