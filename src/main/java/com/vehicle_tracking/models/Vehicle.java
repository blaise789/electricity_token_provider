package com.vehicle_tracking.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String chassisNumber;

    @NotBlank
    private String manufactureCompany;

    @NotNull
    private Integer manufactureYear;

    @NotNull
    @Positive
    private BigDecimal initialPrice;

    @NotBlank
    private String modelName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_owner_id", nullable = false)
    private Owner currentOwner;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plate_number_id", nullable = false)
    private PlateNumber plateNumber;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleTransfer> transferHistory = new ArrayList<>();

    public Vehicle(String chassisNumber, String manufactureCompany, Integer manufactureYear,
                   BigDecimal initialPrice, String modelName, Owner currentOwner, PlateNumber plateNumber) {
        this.chassisNumber = chassisNumber;
        this.manufactureCompany = manufactureCompany;
        this.manufactureYear = manufactureYear;
        this.initialPrice = initialPrice;
        this.modelName = modelName;
        this.currentOwner = currentOwner;
        this.plateNumber = plateNumber;
    }
}
