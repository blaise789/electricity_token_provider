package com.vehicle_tracking.vehicle_tracking.models;


import com.vehicle_tracking.vehicle_tracking.audits.EntityAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle_transfers")
public class VehicleTransfer  extends EntityAudit {
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
    private Plate previousPlate;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_plate_id", nullable = false)
    private Plate newPlate;

    @NotNull
    @Positive
    private double transferAmount=0.0;

    @NotNull
    private LocalDateTime transferDate=LocalDateTime.now();


}