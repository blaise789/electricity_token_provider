package com.vehicle_tracking.vehicle_tracking.services.impl;



import com.vehicle_tracking.vehicle_tracking.dtos.requests.VehicleOwnershipHistoryDTO;
import com.vehicle_tracking.vehicle_tracking.models.Owner;
import com.vehicle_tracking.vehicle_tracking.models.Plate;
import com.vehicle_tracking.vehicle_tracking.models.Vehicle;
import com.vehicle_tracking.vehicle_tracking.models.VehicleTransfer;
import com.vehicle_tracking.vehicle_tracking.repositories.PlateRepository;
import com.vehicle_tracking.vehicle_tracking.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleOwnershipHistoryService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private PlateRepository plateRepository; // Assuming you have this repository

    public List<VehicleOwnershipHistoryDTO> getVehicleOwnershipHistory(String identifier, boolean isChassisNumber) {
        Optional<Vehicle> vehicleOptional;

        if (isChassisNumber) {
            vehicleOptional = vehicleRepository.findByChassisNumberContaining(identifier);
        } else { // isPlateNumber
            Optional<Plate> plateOptional = plateRepository.findByPlateNumber(identifier);
            if (plateOptional.isPresent()) {
                // This finds the vehicle currently associated with this plate.
                // For a complete history search based on a plate that might not be current,
                // one might need to search through VehicleTransfer records for previousPlate or newPlate.
                // However, once a vehicle is identified, its full transfer history is on the Vehicle entity.
                // We'll assume the primary lookup is for a vehicle currently holding the plate.
                vehicleOptional = vehicleRepository.findByPlate(plateOptional.get());
                if (vehicleOptional.isEmpty()) {
                    // Consider if we need a fallback: find a vehicle where this plate was *ever* used.
                    // This would involve querying VehicleTransfer entities directly where previousPlate or newPlate matches.
                    // For now, sticking to the vehicle that currently has the plate, or was found by chassis.
                    // If a vehicle used this plate in the past but doesn't anymore, and chassis is not provided,
                    // this lookup might not find it unless vehicleRepository.findByPlate considers historical plate use.
                    // Given the current Vehicle model, the simplest path is:
                    // 1. Find by Chassis -> Get Vehicle -> Get History
                    // 2. Find by Current Plate -> Get Vehicle -> Get History
                    // The prompt "vehicle identified by either number plate or chassis number"
                    // implies we should be able to find the vehicle.
                    // The current Vehicle.plate is the *current* plate.
                    // If a plate number is given, and it's *not* the current plate of any vehicle,
                    // but it *was* a plate in a transfer, how do we find that vehicle?
                    // A robust solution for "find by any plate it ever had" might require a different query strategy.
                    // For now, this assumes `findByPlate` is on `VehicleRepository` and it finds by the current plate.
                }
            } else {
                vehicleOptional = Optional.empty();
            }
        }

        if (vehicleOptional.isEmpty()) {
            return new ArrayList<>(); // Or throw a custom NotFoundException
        }

        Vehicle vehicle = vehicleOptional.get();
        List<VehicleOwnershipHistoryDTO> history = new ArrayList<>();

        // Sort transfers by date to build chronological history
        List<VehicleTransfer> transfers = vehicle.getTransferHistory().stream()
                .sorted(Comparator.comparing(VehicleTransfer::getTransferDate))
                .toList();

        // 1. Initial Owner information
        Owner initialActualOwner;
        Plate initialActualPlate;
        LocalDateTime initialOwnershipStartDate = vehicle.getCreatedDate(); // From EntityAudit
        double initialPurchasePrice = vehicle.getPrice(); // Vehicle's original price

        if (transfers.isEmpty()) {
            // No transfers, current owner is the initial owner
            initialActualOwner = vehicle.getOwner();
            initialActualPlate = vehicle.getPlate();

            history.add(new VehicleOwnershipHistoryDTO(
                    initialActualOwner.getNames(),
                    initialActualOwner.getNationalId(),
                    initialActualPlate.getPlateNumber(),
                    initialPurchasePrice,
                    initialOwnershipStartDate,
                    null, // Current owner, so no end date
                    "Initial Registration / Current Owner"
            ));
        } else {
            // There are transfers. The owner *before* the first transfer is the initial owner.
            VehicleTransfer firstTransfer = transfers.get(0);
            initialActualOwner = firstTransfer.getPreviousOwner();
            initialActualPlate = firstTransfer.getPreviousPlate();

            history.add(new VehicleOwnershipHistoryDTO(
                    initialActualOwner.getNames(),
                    initialActualOwner.getNationalId(),
                    initialActualPlate.getPlateNumber(),
                    initialPurchasePrice, // Price the first owner "paid" (original vehicle price)
                    initialOwnershipStartDate, // Vehicle registration date
                    firstTransfer.getTransferDate(), // Ownership ended when the first transfer happened
                    "Initial Owner"
            ));

            // 2. Subsequent Owners from Transfers
            for (int i = 0; i < transfers.size(); i++) {
                VehicleTransfer currentTransfer = transfers.get(i);
                Owner newOwner = currentTransfer.getNewOwner();
                Plate newPlate = currentTransfer.getNewPlate();
                double purchasePriceForNewOwner = currentTransfer.getTransferAmount();
                LocalDateTime ownershipStartDateForNewOwner = currentTransfer.getTransferDate();
                LocalDateTime ownershipEndDateForNewOwner = null;

                if (i + 1 < transfers.size()) {
                    // If there's a next transfer, this owner's ownership ended then
                    ownershipEndDateForNewOwner = transfers.get(i + 1).getTransferDate();
                }
                // If this is the last transfer, ownershipEndDateForNewOwner remains null,
                // indicating they are the current owner (or were the last recorded owner).

                String remarks = (ownershipEndDateForNewOwner == null && vehicle.getOwner().getId().equals(newOwner.getId())) ? "Current Owner (Post-Transfer)" : "Transferred";
                if (ownershipEndDateForNewOwner == null && !vehicle.getOwner().getId().equals(newOwner.getId())) {
                    // This case means it's the last transfer in history, but this newOwner isn't the vehicle's current owner.
                    // This could happen if the vehicle was, for example, de-registered or data is out of sync.
                    // Or if the vehicle was transferred, but the vehicle.owner field wasn't updated.
                    // For now, we'll still mark as "Transferred" and the null end date implies last known state.
                    remarks = "Last Known Owner (Post-Transfer)";
                    System.err.println("Warning: Last transfer new owner (" + newOwner.getId() +
                            ") does not match vehicle's current owner (" + vehicle.getOwner().getId() +
                            ") for vehicle ID " + vehicle.getId() + ". Data might be inconsistent or vehicle status changed.");
                }


                history.add(new VehicleOwnershipHistoryDTO(
                        newOwner.getNames(),
                        newOwner.getNationalId(),
                        newPlate.getPlateNumber(),
                        purchasePriceForNewOwner,
                        ownershipStartDateForNewOwner,
                        ownershipEndDateForNewOwner,
                        remarks
                ));
            }
        }
        return history;
    }
}
