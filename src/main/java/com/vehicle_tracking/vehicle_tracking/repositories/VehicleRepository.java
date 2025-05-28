package com.vehicle_tracking.vehicle_tracking.repositories;

import com.vehicle_tracking.vehicle_tracking.models.Plate;
import com.vehicle_tracking.vehicle_tracking.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByChassisNumber(String chassisNumber);
    boolean existsByChassisNumber(String chassisNumber);

    Optional<Vehicle> findVehicleByOwner_NationalId(String nationalId);
    Optional<Vehicle> findVehicleByPlate_PlateNumber(String plate);
    Optional<Vehicle> findVehicleByChassisNumber(String chassisNumber);
    Optional<Vehicle> findByChassisNumberContaining(String chassisNumber);
    Optional<Vehicle> findByPlate(Plate plate);
    @Query("SELECT v FROM Vehicle v WHERE " +
            "(:startDate IS NULL OR v.createdDate >= :startDate) AND " +
            "(:endDate IS NULL OR v.createdDate <= :endDate) AND " +
            "(:manufacturer IS NULL OR LOWER(v.manufacturer) LIKE LOWER(CONCAT('%', :manufacturer, '%'))) AND " +
            "(:modelName IS NULL OR LOWER(v.modelName) LIKE LOWER(CONCAT('%', :modelName, '%'))) " +
            "ORDER BY v.createdDate DESC")
    List<Vehicle> findVehiclesByRegistrationCriteria(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("manufacturer") String manufacturer,
            @Param("modelName") String modelName
    );
}

