package com.vehicle_tracking.repositories;

import com.vehicle_tracking.models.Plate;
import com.vehicle_tracking.models.Vehicle;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByChassisNumber(String chassisNumber);
    boolean existsByChassisNumber(String chassisNumber);

    Optional<Vehicle> findVehicleByOwner_NationalId(String nationalId);
    Optional<Vehicle> findVehicleByPlate_PlateNumber(String plate);
    Optional<Vehicle> findVehicleByChassisNumber(String chassisNumber);
    Optional<Vehicle> findByChassisNumberContaining(String chassisNumber);
    Optional<Vehicle> findByPlate(Plate plate);

}
