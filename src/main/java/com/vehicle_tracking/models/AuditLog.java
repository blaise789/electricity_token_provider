 package com.vehicle_tracking.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String username;
    private String action;
    private String methodName;
    @Lob // For potentially long argument strings
    @Column(columnDefinition = "TEXT")
    private String arguments;
    private String ipAddress;
    private String status; // e.g., SUCCESS, FAILURE
    @Lob
    @Column(columnDefinition = "TEXT")
    private String resultOrError; // Store return value or error message
    private Long durationMs;

    public AuditLog(LocalDateTime timestamp, String username, String action, String methodName, String arguments, String ipAddress, String status, String resultOrError, Long durationMs) {
        this.timestamp = timestamp;
        this.username = username;
        this.action = action;
        this.methodName = methodName;
        this.arguments = arguments;
        this.ipAddress = ipAddress;
        this.status = status;
        this.resultOrError = resultOrError;
        this.durationMs = durationMs;
    }
}