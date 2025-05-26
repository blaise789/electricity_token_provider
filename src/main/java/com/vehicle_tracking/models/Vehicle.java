package com.vehicle_tracking.models;
import com.vehicle_tracking.audits.EntityAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "chassisNumber")
        })
public class Vehicle extends EntityAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String chassisNumber;

    @NotBlank
    private String manufacturer;

    @NotNull
    private Integer manufactureYear;

    @Positive(message = " price should be greater than or equal to 1")
    private double price;

    @NotBlank
    private String modelName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_owner_id", nullable = false)
    private Owner owner;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = " plate_id", nullable = false)
    private Plate plate;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleTransfer> transferHistory = new ArrayList<>();

}
