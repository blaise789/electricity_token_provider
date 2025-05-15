package com.vehicle_tracking.repositories;

import com.vehicle_tracking.models.PlateNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlateNumberRepository extends JpaRepository<PlateNumber, Long> {

}
