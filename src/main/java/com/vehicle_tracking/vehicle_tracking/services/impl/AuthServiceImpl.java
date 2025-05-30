package com.vehicle_tracking.vehicle_tracking.services.impl;

import com.vehicle_tracking.vehicle_tracking.dtos.response.AuthResponse;
import com.vehicle_tracking.vehicle_tracking.exceptions.AppFailureException;
import com.vehicle_tracking.vehicle_tracking.models.User;
import com.vehicle_tracking.vehicle_tracking.security.jwt.JwtTokenProvider;
import com.vehicle_tracking.vehicle_tracking.services.IAuthService;
import com.vehicle_tracking.vehicle_tracking.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public AuthResponse login(String email, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = null;
        try {
            jwt = jwtTokenProvider.generateToken(authentication);
        } catch (Exception e) {
            throw new AppFailureException("Error generating token", e);
        }
        User user = this.userService.getByEmail(email);
        return new AuthResponse(jwt, user);

    }

    @Override
    public void resetPassword(String email, String passwordResetToken, String password) {

    }

    @Override
    public void verifyAccount(String activationCode) {

    }

    @Override
    public void initiateAccountVerification(String email) {

    }

    @Override
    public void initiatePasswordReset(String email) {

    }
}
