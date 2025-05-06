package com.electricity_distribution_system.eds.dtos.requests;



import lombok.Getter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
public class LoginDTO {

    @NotBlank
    @Email
    private  String email;

    @NotBlank
    private  String password;
}


