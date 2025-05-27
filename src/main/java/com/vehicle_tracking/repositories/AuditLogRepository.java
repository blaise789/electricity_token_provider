package com.vehicle_tracking.repositories;

import com.vehicle_tracking.models.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    // Add custom query methods if needed, e.g., find by username, action, date range
    Page<AuditLog> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
    Page<AuditLog> findByActionContainingIgnoreCase(String action, Pageable pageable);
}