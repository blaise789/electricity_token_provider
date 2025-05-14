package com.electricity_distribution_system.eds.services.standalone;

import com.electricity_distribution_system.eds.exceptions.BadRequestException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import org.springframework.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
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
