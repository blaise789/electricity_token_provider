package com.vehicle_tracking.repositories;

import com.vehicle_tracking.models.Owner;
import com.vehicle_tracking.models.Plate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlateRepository extends JpaRepository<Plate, Long> {

    public List<Plate> findByExpirationDateBetween(LocalDate from, LocalDate to);
    public Optional<Plate> findByPlateNumber(String plateNumber);
    public Page<Plate> findByOwner(Owner owner, Pageable pageable);

}
