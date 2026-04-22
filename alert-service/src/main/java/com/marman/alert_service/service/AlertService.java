package com.marman.alert_service.service;

import com.marman.alert_service.entity.Alert;
import com.marman.alert_service.repository.AlertRepository;
import com.marman.kafka.event.AlertingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "energy-alerts", groupId = "alert-service")
    public void energyUsageAlertEvent(AlertingEvent alertingEvent){
        log.info("Recive alert event:{}",alertingEvent);

        // send email alert
        final String subject = "Energy Usage Alert for User "
                + alertingEvent.getUserId();
        final String message = "Alert: " + alertingEvent.getMessage() +
                "\nThreshold: " + alertingEvent.getThreshold() +
                "\nEnergy Consumed: " + alertingEvent.getEnergyConsumed();
        emailService.sendEmail(alertingEvent.getEmail(),
                subject,
                message,
                alertingEvent.getUserId());
    }

}
