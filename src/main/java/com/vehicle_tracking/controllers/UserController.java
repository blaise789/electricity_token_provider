package com.vehicle_tracking.controllers;

import com.vehicle_tracking.annotations.Auditable;
import com.vehicle_tracking.dtos.requests.CreateUserDTO;
import com.vehicle_tracking.dtos.requests.UpdateUserDTO;
import com.vehicle_tracking.dtos.response.ApiResponseDTO;
import com.vehicle_tracking.enums.ERole;
import com.vehicle_tracking.models.Role;
import com.vehicle_tracking.models.User;
import com.vehicle_tracking.repositories.RoleRepository;
import com.vehicle_tracking.services.IFileService;
import com.vehicle_tracking.services.IUserService;
import com.vehicle_tracking.utils.Constants;
import com.vehicle_tracking.utils.Utility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.vehicle_tracking.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController

public class UserController {
    private final IUserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final IFileService fileService;
    @Value("${uploads.directory.user_profiles}")
    private String  userProfilesDirectory;






//    createUser

    @PostMapping("/register")
    @Auditable(action = "USER_SIGNUP_ATTEMPT")
    public ResponseEntity<ApiResponseDTO> register(@Valid @RequestBody    CreateUserDTO dto) throws BadRequestException {
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
        return ResponseEntity.created(uri).body(ApiResponseDTO.success("User Created successfully",createdUser));
    }


    @GetMapping(path = "/all/{role}")
    public ResponseEntity<ApiResponseDTO> getAllUsersByRole(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @PathVariable(value = "role") ERole role
    ) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok(ApiResponseDTO.success("Users fetched successfully", userService.getAllByRole(pageable, role)));
    }

    @GetMapping(path = "/search")
    public Page<User> searchUsers(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @RequestParam(value = "searchKey") String searchKey
    ) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return userService.searchUser(pageable, searchKey);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponseDTO> getById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(ApiResponseDTO.success("User fetched successfully", this.userService.getById(id)));
    }


    @GetMapping(path = "/current-user")
    public ResponseEntity<ApiResponseDTO> currentlyLoggedInUser() {
        return ResponseEntity.ok(ApiResponseDTO.success("Currently logged in user fetched", userService.getLoggedInUser()));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<ApiResponseDTO> update(@RequestBody UpdateUserDTO dto) {
        User updated = this.userService.update(this.userService.getLoggedInUser().getId(), dto);
        return ResponseEntity.ok(ApiResponseDTO.success("User updated successfully", updated));
    }


    @PutMapping(path = "/upload-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDTO> uploadProfileImage(
            @RequestParam("file") MultipartFile document
    ) {
        if (!Utility.isImageFile(document)) {
            throw new BadRequestException("Only image files are allowed");
        }
        User user = this.userService.getLoggedInUser();
        User updated = this.userService.changeProfileImage(user.getId(), document);
        return ResponseEntity.ok(ApiResponseDTO.success("File saved successfully", updated));

    }

//    @PatchMapping(path = "/remove-profile")
//    public ResponseEntity<ApiResponseDTO> removeProfileImage() {
//        User user = this.userService.getLoggedInUser();
//        User updated = this.userService.removeProfileImage(user.getId());
//        return ResponseEntity.ok(ApiResponseDTO.success("Profile image removed successfully", updated));
//    }
//
//    @DeleteMapping("/delete")
//    public ResponseEntity<ApiResponseDTO> deleteMyAccount() {
//        User user = this.userService.getLoggedInUser();
//        this.userService.delete(user.getId());
//        return ResponseEntity.ok(ApiResponseDTO.success("Account deleted successfully"));
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<ApiResponseDTO> deleteByAdmin(
//            @PathVariable(value = "id") UUID id
//    ) {
//        this.userService.delete(id);
//        return ResponseEntity.ok(ApiResponseDTO.success("Account deleted successfully"));
//    }
}
