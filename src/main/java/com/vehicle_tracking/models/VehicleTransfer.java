package com.vehicle_tracking.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle_transfers")
public class VehicleTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_owner_id", nullable = false)
    private Owner previousOwner;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_owner_id", nullable = false)
    private Owner newOwner;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_plate_id", nullable = false)
    private PlateNumber previousPlate;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_plate_id", nullable = false)
    private PlateNumber newPlate;

    @NotNull
    @Positive
    private BigDecimal transferAmount;

    @NotNull
    private LocalDateTime transferDate;

    public VehicleTransfer(Vehicle vehicle, Owner previousOwner, Owner newOwner,
                           PlateNumber previousPlate, PlateNumber newPlate,
                           BigDecimal transferAmount) {
        this.vehicle = vehicle;
        this.previousOwner = previousOwner;
        this.newOwner = newOwner;
        this.previousPlate = previousPlate;
        this.newPlate = newPlate;
        this.transferAmount = transferAmount;
        this.transferDate = LocalDateTime.now();
    }
}