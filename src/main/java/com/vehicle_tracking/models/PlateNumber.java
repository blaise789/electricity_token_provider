package com.vehicle_tracking.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "plate_numbers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "plateNumber")
        })
public class PlateNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String plateNumber;

    @NotNull
    private LocalDate issuedDate;

    @Enumerated(EnumType.STRING)
    private PlateStatus status = PlateStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @OneToOne(mappedBy = "plateNumber", fetch = FetchType.LAZY)
    private Vehicle currentVehicle;

    public enum PlateStatus {
        AVAILABLE,
        IN_USE
    }

    public PlateNumber(String plateNumber, LocalDate issuedDate, Owner owner) {
        this.plateNumber = plateNumber;
        this.issuedDate = issuedDate;
        this.owner = owner;
    }
    }
