package com.vehicle_tracking.vehicle_tracking.services;

import com.vehicle_tracking.vehicle_tracking.dtos.response.AuthResponse;

public interface IAuthService {
    AuthResponse login(String email, String password);
    void resetPassword(String email,String passwordResetToken, String password);
    void verifyAccount(String activationCode);
    void initiateAccountVerification(String email);
    void initiatePasswordReset(String email);


}
