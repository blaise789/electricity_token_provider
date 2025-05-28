package com.vehicle_tracking.vehicle_tracking.dtos.requests;

import com.vehicle_tracking.vehicle_tracking.enums.EGender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateUserDTO {
    @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Pattern(regexp = "[0-9]{12}")
    private String telephone;


    private EGender gender;

}