package com.vehicle_tracking.repositories;

import com.vehicle_tracking.models.VehicleTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VehicleTransferRepository extends JpaRepository<VehicleTransfer, Long> {
    @Query("SELECT vt FROM VehicleTransfer vt WHERE " +
            "(:startDate IS NULL OR vt.transferDate >= :startDate) AND " +
            "(:endDate IS NULL OR vt.transferDate <= :endDate) " +
            "ORDER BY vt.transferDate DESC")
    List<VehicleTransfer> findTransfersByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
