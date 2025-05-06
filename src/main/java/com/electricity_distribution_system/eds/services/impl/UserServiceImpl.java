package com.electricity_distribution_system.eds.services.impl;

import com.electricity_distribution_system.eds.enums.ERole;
import com.electricity_distribution_system.eds.enums.EUserStatus;
import com.electricity_distribution_system.eds.exceptions.BadRequestException;
import com.electricity_distribution_system.eds.models.User;
import com.electricity_distribution_system.eds.repositories.UserRepository;
import com.electricity_distribution_system.eds.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    @Override
    public Page<User> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public User getById(long id) {
        return null;
    }

    @Override
    public User create(User user) throws BadRequestException {

        try {
            Optional<User> userOptional = this.userRepository.findByEmail(user.getEmail());
            if (userOptional.isPresent())
                throw new BadRequestException(String.format("User with email '%s' already exists", user.getEmail()));
            return this.userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
//            String errorMessage = Utility.getConstraintViolationMessage(ex, user);
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Page<User> getAllByRole(Pageable pageable, ERole role) {
        return null;
    }

    @Override
    public Page<User> searchUser(Pageable pageable, String searchKey) {
        return null;
    }

    @Override
    public User getLoggedInUser() {
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public User changeStatus(UUID id, EUserStatus status) {
        return null;
    }

    @Override
    public User changeProfileImage(UUID id, File file) {
        return null;
    }

    @Override
    public User removeProfileImage(UUID id) {
        return null;
    }

    @Override
    public Optional<User> findByActivationCode(String verificationCode) {
        return Optional.empty();
    }
}
