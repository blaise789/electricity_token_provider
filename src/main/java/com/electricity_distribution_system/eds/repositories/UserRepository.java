package com.electricity_distribution_system.eds.repositories;

import com.electricity_distribution_system.eds.enums.ERole;
import com.electricity_distribution_system.eds.models.Role;
import com.electricity_distribution_system.eds.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findById(UUID userID);

    Optional<User> findByEmail(String email);
//    Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :role")
    Page<User> findByRoleName(Pageable pageable, @Param("role") ERole role);
//    Page<User> findByRoles(Pageable pageable, ERole role);
//    Page<User> (Pageable pageable, String email);
//    Optional<User> findByActivationCode(String activationCode);
}
