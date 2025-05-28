package com.vehicle_tracking.vehicle_tracking.controllers;// package com.vehicle_tracking.vehicle_tracking.controllers;

import com.vehicle_tracking.vehicle_tracking.dtos.response.PagedResponse;
import com.vehicle_tracking.vehicle_tracking.models.AuditLog;
import com.vehicle_tracking.vehicle_tracking.repositories.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/logs")
@PreAuthorize("hasRole('ADMIN')")
public class AuditLogController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @GetMapping
    public ResponseEntity<PagedResponse<AuditLog>> getAuditLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String action
    ) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<AuditLog> auditLogPage;
        if (username != null && !username.isEmpty()) {
            auditLogPage = auditLogRepository.findByUsernameContainingIgnoreCase(username, pageable);
        } else if (action != null && !action.isEmpty()) {
            auditLogPage = auditLogRepository.findByActionContainingIgnoreCase(action, pageable);
        } else {
            auditLogPage = auditLogRepository.findAll(pageable);
        }

        // PagedResponse is a generic wrapper you'd create for paginated results
        PagedResponse<AuditLog> response = new PagedResponse<>(
                auditLogPage.getContent(),
                auditLogPage.getNumber(),
                auditLogPage.getSize(),
                auditLogPage.getTotalElements(),
                auditLogPage.getTotalPages(),
                auditLogPage.isLast()
        );
        return ResponseEntity.ok(response);
    }
}