package com.electricity_distribution_system.eds.dtos.requests;

import com.electricity_distribution_system.eds.enums.EGender;
import com.electricity_distribution_system.eds.enums.ERole;
import com.electricity_distribution_system.eds.validators.ValidNationalId;
import com.electricity_distribution_system.eds.validators.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data

public class CreateUserDTO {

    @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Pattern(regexp = "[0-9]{9,12}", message = "Your phone is not a valid tel we expect 2507***, or 07*** or 7***")
    private String telephone;

    @NotBlank
//    validate the goverment_id
    @ValidNationalId
    private String government_id;

    private EGender gender;

    private ERole role;

    @ValidPassword
    private String password;
}
