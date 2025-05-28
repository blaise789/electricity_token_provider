package com.vehicle_tracking.vehicle_tracking.repositories;

import com.vehicle_tracking.vehicle_tracking.enums.ERole;
import com.vehicle_tracking.vehicle_tracking.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}
