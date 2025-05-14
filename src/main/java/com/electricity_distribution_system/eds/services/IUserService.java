package com.electricity_distribution_system.eds.services;

import com.electricity_distribution_system.eds.dtos.requests.UpdateUserDTO;
import com.electricity_distribution_system.eds.enums.ERole;
import com.electricity_distribution_system.eds.enums.EUserStatus;
import com.electricity_distribution_system.eds.exceptions.BadRequestException;
import com.electricity_distribution_system.eds.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.electricity_distribution_system.eds.models.File;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
Page<User> getAll(Pageable pageable);
User getById(UUID id);
User create(User user) throws BadRequestException;
User update(UUID id,UpdateUserDTO userDTO) throws BadRequestException;
User save(User user);

Page<User> getAllByRole(Pageable pageable, ERole role);

Page<User> searchUser(Pageable pageable, String searchKey);

User getLoggedInUser();

    User getByEmail(String email);
    User changeStatus(UUID id, EUserStatus status);

    User changeProfileImage(UUID id, File file);

    User removeProfileImage(UUID id);

    Optional<User> findByActivationCode(String verificationCode);



}
