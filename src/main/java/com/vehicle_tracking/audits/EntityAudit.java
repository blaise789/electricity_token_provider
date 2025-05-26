package com.vehicle_tracking.audits;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.util.UUID;

import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class EntityAudit {


    @CreatedDate
    @Column(name = "created_date", updatable = false, nullable = true)
    private LocalDateTime createdDate;

    @CreatedBy
    @Column(name = "created_by", updatable = false, nullable = true)
    private UUID createdBy;

    @LastModifiedDate
    @Column(insertable = false, nullable = true)
    private LocalDateTime lastModifiedDate;

    @LastModifiedBy
    @Column(insertable = false, nullable = true)
    private UUID lastModifiedBy;



}