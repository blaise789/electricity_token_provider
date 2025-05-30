package com.vehicle_tracking.vehicle_tracking.dtos.requests;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginDTO {

    @NotBlank
    @Email
    private  String email;
    @NotBlank
    private  String password;
}


