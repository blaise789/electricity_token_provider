package com.vehicle_tracking.repositories;

import com.vehicle_tracking.models.VehicleTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTransferRepository extends JpaRepository<VehicleTransfer, Long> {
}
