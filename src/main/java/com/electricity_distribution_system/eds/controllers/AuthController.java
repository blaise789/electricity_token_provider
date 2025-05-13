package com.electricity_distribution_system.eds.controllers;

import com.electricity_distribution_system.eds.dtos.requests.LoginDTO;
import com.electricity_distribution_system.eds.dtos.response.ApiResponse;
import com.electricity_distribution_system.eds.exceptions.BadRequestException;
import jakarta.validation.Valid;
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
public class AuthController {
    @PostMapping("")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginDTO dto) throws BadRequestException {
        log.info("Login request: {}", dto);
        return ResponseEntity.ok(ApiResponse.success("Login successful"));
    }


}
