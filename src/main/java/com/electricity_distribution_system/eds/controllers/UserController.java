package com.electricity_distribution_system.eds.controllers;

import com.electricity_distribution_system.eds.dtos.requests.CreateUserDTO;
import com.electricity_distribution_system.eds.dtos.requests.LoginDTO;
import com.electricity_distribution_system.eds.dtos.response.ApiResponse;
import com.electricity_distribution_system.eds.models.Role;
import com.electricity_distribution_system.eds.models.User;
import com.electricity_distribution_system.eds.repositories.RoleRepository;
import com.electricity_distribution_system.eds.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.electricity_distribution_system.eds.exceptions.BadRequestException ;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Slf4j
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController

public class UserController {
    private final IUserService userService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;






//    createUser

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register( @Valid @RequestBody    CreateUserDTO dto) throws BadRequestException {
        User user = new User();

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Role role = roleRepository.findByName(dto.getRole()).orElseThrow(
                () -> new BadRequestException("User Role not set"));

        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setGender(dto.getGender());

        user.setTelephone(dto.getTelephone());
        user.setPassword(encodedPassword);
        user.setRoles(Collections.singleton(role));
        User createdUser = userService.create(user);
        log.info("Created user: {}", createdUser);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toString());
        return ResponseEntity.created(uri).body(ApiResponse.success("User Created successfully",createdUser));
    }

}
