package com.vehicle_tracking.vehicle_tracking.dtos.requests;

import com.vehicle_tracking.vehicle_tracking.enums.EGender;
import com.vehicle_tracking.vehicle_tracking.enums.ERole;
import com.vehicle_tracking.vehicle_tracking.validators.ValidNationalId;
import com.vehicle_tracking.vehicle_tracking.validators.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data

public class CreateUserDTO {

    @Email(message = "Invalid email given.")
    private String email;

    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "[0-9]{9,12}", message = "Your phone is not a valid tel we expect 2507***, or 07*** or 7***")
    private String telephone;

    @NotBlank(message = "ID is required.")
//    validate the goverment_id
    @ValidNationalId
    private String government_id;

    private EGender gender;

    private ERole role;

    @ValidPassword(message = "Password is invalid")
    private String password;
}
