package com.vehicle_tracking.repositories;

import com.vehicle_tracking.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
