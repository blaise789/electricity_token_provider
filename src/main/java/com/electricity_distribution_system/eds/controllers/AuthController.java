package com.electricity_distribution_system.eds.controllers;

import com.electricity_distribution_system.eds.dtos.requests.LoginDTO;
import com.electricity_distribution_system.eds.dtos.response.ApiResponse;
import com.electricity_distribution_system.eds.dtos.response.AuthResponse;
import com.electricity_distribution_system.eds.exceptions.BadRequestException;
import com.electricity_distribution_system.eds.services.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginDTO dto) throws BadRequestException {
        log.info("Login request: {}", dto);
        AuthResponse authResponse= authService.login(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(ApiResponse.success("Login successful",authResponse));
    }


}
