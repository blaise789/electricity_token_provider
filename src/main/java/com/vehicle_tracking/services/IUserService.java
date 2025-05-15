package com.vehicle_tracking.services;

import com.vehicle_tracking.dtos.requests.UpdateUserDTO;
import com.vehicle_tracking.enums.ERole;
import com.vehicle_tracking.enums.EUserStatus;
import com.vehicle_tracking.exceptions.BadRequestException;
import com.vehicle_tracking.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {
Page<User> getAll(Pageable pageable);
User getById(UUID id);
User create(User user) throws BadRequestException;
User update(UUID id,UpdateUserDTO userDTO) throws BadRequestException;
User save(User user);

Page<User> getAllByRole(Pageable pageable,  ERole role);

Page<User> searchUser(Pageable pageable, String searchKey);

User getLoggedInUser();

    User getByEmail(String email);
    User changeStatus(UUID id, EUserStatus status);

    User changeProfileImage(UUID id, MultipartFile file);

    User removeProfileImage(UUID id);

    Optional<User> findByActivationCode(String verificationCode);



}
