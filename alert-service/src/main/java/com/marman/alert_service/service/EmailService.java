package com.marman.alert_service.service;

import com.marman.alert_service.entity.Alert;
import com.marman.alert_service.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final AlertRepository alertRepository;

    public void sendEmail(
        String to,
        String subject,
        String body,
        Long userId
    ){
        log.info("Sending email to:{}, subject:{}",to,subject);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("noreply@marman.com");
        message.setSubject(subject);
        message.setText(body);

        try{

            javaMailSender.send(message);
            saveAlert(true,userId);

        }catch (MatchException e){

            log.error("Failed to send email to: {}", to, e);
            saveAlert(false,userId);

        }
    }

    public void saveAlert(Boolean sent,Long userId){
        final Alert alert = Alert.builder()
                .sent(sent)
                .createdAt(LocalDateTime.now())
                .userId(userId)
                .build();
        alertRepository.saveAndFlush(alert);
    }

}
