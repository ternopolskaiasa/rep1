package org.example.controller;

import org.example.model.UserOperationEvent;
import org.example.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserNotificationController {
    private final EmailService emailService;

    public UserNotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-notification")
    @ResponseStatus(HttpStatus.OK)
    public void sendManualNotification(@RequestBody UserOperationEvent event) {
        emailService.sendNotificationEmail(event);
    }
}
