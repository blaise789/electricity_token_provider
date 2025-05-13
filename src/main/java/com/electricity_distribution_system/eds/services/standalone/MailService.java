package com.electricity_distribution_system.eds.services.standalone;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class MailService {
//    sendWelcomeEmail
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
//    @Value("${app.frontend.reset-password}")
    private String resetPasswordUrl;
//    @Value("${app.frontend.support-email")
    private String supportEmail;

//send passwordResetEmail
 public void sendResetPassword(String email) {
     try{
//         create mesage and set it  to  the email
     }
     catch(Exception exception){
//         catch tbe messaging exception through
     }
 }
 //activateAccountEmail
    public void sendActivationEmail(String email,String fullName,String  verificationCode){

    }

    public void sendVerificationSuccessFulEmail(String to,String fullName){


    }
    public void sendPasswordResetSuccessfully(String to,String fullName){}
}
