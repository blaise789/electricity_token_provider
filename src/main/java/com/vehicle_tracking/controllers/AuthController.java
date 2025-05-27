package com.vehicle_tracking.controllers;

import com.vehicle_tracking.annotations.Auditable;
import com.vehicle_tracking.dtos.requests.LoginDTO;
import com.vehicle_tracking.dtos.response.ApiResponseDTO;
import com.vehicle_tracking.dtos.response.AuthResponse;
import com.vehicle_tracking.exceptions.BadRequestException;
import com.vehicle_tracking.services.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    @Auditable(action = "LOGIN_ATTEMPT")
    public ResponseEntity<ApiResponseDTO> login(@Valid @RequestBody LoginDTO dto) throws BadRequestException {
        log.info("Login request: {}", dto);
        AuthResponse authResponse= authService.login(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(ApiResponseDTO.success("Login successful",authResponse));
    }


}
