package com.vehicle_tracking.services.impl;

import com.vehicle_tracking.dtos.requests.CreateTransferDTO;
import com.vehicle_tracking.dtos.response.VehicleTransferResponseDTO;
import com.vehicle_tracking.enums.EPlateStatus;
import com.vehicle_tracking.exceptions.ResourceAlreadyExistsException;
import com.vehicle_tracking.exceptions.ResourceNotFoundException;
import com.vehicle_tracking.models.Owner;
import com.vehicle_tracking.models.Plate;
import com.vehicle_tracking.models.Vehicle;
import com.vehicle_tracking.models.VehicleTransfer;
import com.vehicle_tracking.repositories.OwnerRepository;
import com.vehicle_tracking.repositories.PlateRepository;
import com.vehicle_tracking.repositories.TransferRepository;
import com.vehicle_tracking.repositories.VehicleRepository;
import com.vehicle_tracking.services.ITransferService;
import com.vehicle_tracking.services.standalone.MailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferService implements ITransferService {
    private final TransferRepository transferRepo;
    private final VehicleRepository vehicleRepo;
    private final OwnerRepository ownerRepo;
    private final   PlateRepository plateRepo;
    private final MailService mailService;
    private final TransferRepository transferRepository;

    @Override
    @Transactional
    public void createTransfer(CreateTransferDTO dto) {
//        check the old owner if already exists
        Owner oldOwner = ownerRepo.findByEmailAndNationalId(
                dto.getOldOwnerEmail(),
                dto.getOldOwnerNationalID()).orElseThrow(
                () -> new ResourceAlreadyExistsException(" no owner found with that email and national id"));

        Owner newOwner = ownerRepo.findByEmailAndNationalId(
                dto.getNewOwnerEmail(),
                dto.getNewOwnerNationalID()).orElseThrow(
                () -> new ResourceAlreadyExistsException(" no owner found with that email and national id"));

        Vehicle vehicle = vehicleRepo.findByChassisNumber(dto.getVehicleChassisNumber()).orElseThrow(
                () -> new ResourceAlreadyExistsException(" no vehicle found with that chassis number"));

        log.info(dto.getNewPlateNumber());
        Plate plate = plateRepo.findByPlateNumber(dto.getNewPlateNumber()).orElseThrow(
                () -> new ResourceNotFoundException(" no plate found with that plate number","plate_number",dto.getNewPlateNumber()));



        if (plate.getOwner() != newOwner)
            throw new ResourceAlreadyExistsException(" this plate does not belong to this new  owner");
        if (plate.getStatus() == EPlateStatus.IN_USE || plate.getVehicle() != null)
            throw new ResourceAlreadyExistsException(" this plate is already in use");
        if (!vehicle.getOwner().getId().equals(oldOwner.getId()))
            throw new ResourceAlreadyExistsException(" this vehicle does not belong to the old owner");


        if (vehicle.getOwner().getId().equals(newOwner.getId()))
            throw new ResourceAlreadyExistsException(" this vehicle already belongs to the new owner");
        Plate oldPlate = vehicle.getPlate();
        oldPlate.setStatus(EPlateStatus.AVAILABLE);
        oldPlate.setVehicle(null);
        plateRepo.save(oldPlate);

        plate.setStatus(EPlateStatus.IN_USE);
        plateRepo.save(plate);

        vehicle.setPlate(plate);
        vehicle.setOwner(newOwner);
        vehicleRepo.save(vehicle);

        VehicleTransfer transfer = new VehicleTransfer();
        transfer.setPreviousOwner(oldOwner);
        transfer.setPreviousPlate(oldPlate);
        transfer.setNewOwner(newOwner);
        transfer.setNewPlate(plate);
        transfer.setVehicle(vehicle);
        transfer.setTransferAmount(dto.getAmount());

        // Send Emails
        log.info("Creating transfer with: vehicle={}, prevOwner={}, newOwner={}, prevPlate={}, newPlate={}, amount={}",
                vehicle.getId(),
                oldOwner.getId(),
                newOwner.getId(),
                oldPlate.getId(),
                plate.getId(),
                dto.getAmount()
        );
        mailService.sendTransferNotification(oldOwner.getEmail(), newOwner.getEmail(), vehicle);
        transferRepository.save(transfer);


    }

    @Override
    @Transactional()
    public Page<VehicleTransfer> getVehicleHistory(String chassisNumber, String plateNumber, Pageable pageable) {
        Vehicle vehicle = null;
        if (chassisNumber != null && !chassisNumber.isBlank() && plateNumber != null && !plateNumber.isBlank()) {
            // If both are provided, verify both match the same vehicle
            Vehicle vehicleFetched = vehicleRepo.findByChassisNumber(chassisNumber)
                    .orElseThrow(
                            () -> new ResourceNotFoundException("VehicleFetched with chassis number not found","chassis number",chassisNumber));

            if (vehicleFetched.getPlate() == null || !vehicleFetched.getPlate().getPlateNumber().equals(plateNumber)) {
                throw new ResourceAlreadyExistsException(
                        "Plate number and chassis number do not match the same vehicle");
            }

            vehicle = vehicleFetched;

        } else if (chassisNumber != null && !chassisNumber.isBlank()) {
            vehicle = vehicleRepo.findByChassisNumber(chassisNumber)
                    .orElseThrow(() -> new ResourceAlreadyExistsException("Vehicle with chassis number not found"));

        } else if (plateNumber != null && !plateNumber.isBlank()) {
            Plate plate = plateRepo.findByPlateNumber(plateNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Plate number not found","plate number",plateNumber));
            vehicle = plate.getVehicle();
            if (vehicle == null) {
                throw new ResourceNotFoundException("No vehicle assigned to this plate","plate_number",plateNumber);
            }

        } else {
            throw new IllegalArgumentException("You must provide either a chassis number or a plate number");
        }
        log.info("vehicle {}",vehicle.getPlate().getPlateNumber());

        return transferRepo.findByVehicle(vehicle, pageable);
    }
}
