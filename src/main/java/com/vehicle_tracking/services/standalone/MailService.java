package com.vehicle_tracking.services.standalone;

import lombok.RequiredArgsConstructor;

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
     
 }
}
