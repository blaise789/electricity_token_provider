package com.electricity_distribution_system.eds.repositories;

import com.electricity_distribution_system.eds.enums.ERole;
import com.electricity_distribution_system.eds.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}
