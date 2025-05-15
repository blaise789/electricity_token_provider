package com.electricity_distribution_system.eds.repositories;

import com.electricity_distribution_system.eds.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
    Optional<File> getFileByName(String name);

}
