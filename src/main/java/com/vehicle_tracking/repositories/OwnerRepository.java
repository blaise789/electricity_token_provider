package com.vehicle_tracking.repositories;

import com.vehicle_tracking.models.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

     Optional<Owner> findByEmail(String email);
     Optional<Owner> findByEmailAndNationalId(String email, String nationalId);

}
