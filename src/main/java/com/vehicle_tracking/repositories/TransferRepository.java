package com.vehicle_tracking.repositories;

import com.vehicle_tracking.models.Vehicle;
import com.vehicle_tracking.models.VehicleTransfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<VehicleTransfer,Long> {
    Page<VehicleTransfer> findByVehicle(Vehicle vehicle, Pageable pageable);
}
