package com.vehicle_tracking.schedulers;

import com.vehicle_tracking.models.Plate;
import com.vehicle_tracking.repositories.PlateRepository;
import com.vehicle_tracking.services.standalone.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LicensePlateExpirationScheduler {

    private final PlateRepository plateNumberRepository;
    private final MailService mailService;

    // Modified to run at 17:38 (5:38 PM) every day for testing
    @Scheduled(cron = "0 43 17 * * ?")
    public void checkExpiringPlates() {
        log.info("Scheduler started at {}", LocalDateTime.now());

        LocalDate warningDate = LocalDate.now().plusDays(30);
        List<Plate> expiringPlates = plateNumberRepository.findByExpirationDateBetween(LocalDate.now(), warningDate);

        log.info("Found {} expiring plates", expiringPlates.size());

        expiringPlates.forEach(plate -> {
            try {
                mailService.sendExpirationWarning(
                        plate.getOwner(),
                        plate.getPlateNumber(),
                        plate.getExpirationDate()
                );
                log.info("Sent expiration warning for plate {}", plate.getPlateNumber());
            } catch (Exception e) {
                log.error("Failed to send warning for plate {}: {}", plate.getPlateNumber(), e.getMessage());
            }
        });

        log.info("Scheduler completed at {}", LocalDateTime.now());
    }
}