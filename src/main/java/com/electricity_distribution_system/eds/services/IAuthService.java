package com.electricity_distribution_system.eds.services;

import com.electricity_distribution_system.eds.dtos.response.AuthResponse;

public interface IAuthService {
    AuthResponse login(String email, String password);
    void resetPassword(String email,String passwordResetToken, String password);
    void verifyAccount(String activationCode);
    void initiateAccountVerification(String email);
    void initiatePasswordReset(String email);


}
