package com.vehicle_tracking.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "owners",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "nationalId"),
                @UniqueConstraint(columnNames = "phone")
        })
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String names;

    @NotBlank
    @Size(max = 20)
    private String nationalId;

    @NotBlank
    @Size(max = 15)
    private String phone;

    @NotBlank
    @Size(max = 255)
    private String address;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlateNumber> plateNumbers = new ArrayList<>();

    public Owner(String names, String nationalId, String phone, String address) {
        this.names = names;
        this.nationalId = nationalId;
        this.phone = phone;
        this.address = address;
    }
}