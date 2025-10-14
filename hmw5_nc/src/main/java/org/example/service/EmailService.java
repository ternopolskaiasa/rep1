package org.example.service;

import org.example.model.UserOperationEvent;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotificationEmail(UserOperationEvent event) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getEmail());
        message.setSubject("Notification about account's changes");
        message.setText(String.format("User with id %s: operation happened '%s'.", event.getUserId(), event.getOperationType()));
        javaMailSender.send(message);
    }
}
