package com.electricity_distribution_system.eds.services;

public interface IAuthService {
    void login(String email,String password);
    void resetPassword(String email,String passwordResetToken, String password);
    void verifyAccount(String activationCode);
    void initiateAccountVerification(String email);
    void initiatePasswordReset(String email);


}
