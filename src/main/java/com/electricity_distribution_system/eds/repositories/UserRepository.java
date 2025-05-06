package com.electricity_distribution_system.eds.repositories;

import com.electricity_distribution_system.eds.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findById(UUID userID);

    Optional<User> findByEmail(String email);
}
