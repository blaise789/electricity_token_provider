package com.vehicle_tracking.models;


import com.vehicle_tracking.audits.EntityAudit;
import com.vehicle_tracking.enums.EPlateStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "plates",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "plateNumber")
        })
public class Plate extends EntityAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String plateNumber;

    @NotNull
    private LocalDate issuedDate;

    @Enumerated(EnumType.STRING)
    private EPlateStatus status = EPlateStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @OneToOne(mappedBy = "plate", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Vehicle vehicle;



    @Column(nullable = false)
    private LocalDate expirationDate;
    public Plate(String plateNumber, LocalDate issuedDate, Owner owner) {
        this.plateNumber = plateNumber;
        this.issuedDate = issuedDate;
        this.owner = owner;
    }
    }
