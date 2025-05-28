package com.vehicle_tracking.vehicle_tracking.services.standalone;

import com.vehicle_tracking.vehicle_tracking.models.Owner;
import com.vehicle_tracking.vehicle_tracking.models.Vehicle;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
//    sendWelcomeEmail
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
//    @Value("${app.frontend.reset-password}")
    private String resetPasswordUrl;
//    @Value("${app.frontend.support-email")
    private String supportEmail;

//send passwordResetEmail
public void sendExpirationWarning(Owner owner, String plateNumber, LocalDate expirationDate) {
    String subject = "License Plate Expiration Warning";
    Context context = new Context();
    context.setVariable("ownerName", owner.getNames());
    context.setVariable("plateNumber", plateNumber);
    context.setVariable("expirationDate", expirationDate);
    context.setVariable("daysRemaining", ChronoUnit.DAYS.between(LocalDate.now(), expirationDate));

    String content = templateEngine.process("license-plate-expiration", context);
    sendEmailNotification(owner.getEmail(), subject, content);
}
    public void sendTransferNotification(String oldOwnerEmail, String newOwnerEmail, Vehicle vehicle) {
        String subject = "Vehicle Ownership Transfer Notification";

        String oldOwnerMessage = String.format(
                "Dear Customer,\n\n" +
                        "This is to inform you that your vehicle (Chassis Number: %s, Model: %s) has been successfully transferred to a new owner.\n\n" +
                        "Thank you for using our services.\n\n" +
                        "Rwanda Revenue Authority.",
                vehicle.getChassisNumber(), vehicle.getModelName()
        );

        String newOwnerMessage = String.format(
                "Dear Customer,\n\n" +
                        "Congratulations! You are now the new owner of the vehicle (Chassis Number: %s, Model: %s).\n" +
                        "Please ensure to complete any remaining formalities if required.\n\n" +
                        "Thank you for choosing our services.\n\n" +
                        "Rwanda Revenue Authority.",
                vehicle.getChassisNumber(), vehicle.getModelName()
        );

        // Use your EmailService to send emails
        sendEmailNotification(oldOwnerEmail, subject, oldOwnerMessage);
        sendEmailNotification(newOwnerEmail, subject, newOwnerMessage);
    }
    public void sendEmailNotification(String email, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true); // true indicates HTML

            mailSender.send(message);
            log.info("Email notification sent to {}", email);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}", email, e);
        }
        }
    }
