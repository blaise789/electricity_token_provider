package com.vehicle_tracking.vehicle_tracking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vehicle_tracking.vehicle_tracking.audits.EntityAudit;
import com.vehicle_tracking.vehicle_tracking.enums.EGender;
import com.vehicle_tracking.vehicle_tracking.enums.EUserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}), @UniqueConstraint(columnNames = {"telephone"})})
public class User extends EntityAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;


    @NotBlank
    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "telephone")
    private String telephone;
    @Column(name = "national_id")
    private String nationalId;
    @JsonIgnore
    @NotBlank
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private EGender gender;


    @JoinColumn(name = "profile_image_id")
    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private File profileImage;

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EUserStatus status = EUserStatus.PENDING;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }


}
