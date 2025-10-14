package org.example;

import jakarta.mail.internet.MimeMessage;
import org.example.model.UserOperationEvent;
import org.example.service.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class EmailServiceTest {

    @Mock
    private JavaMailSender mockMailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void testSendNotificationEmailSuccess() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(emailService, "javaMailSender", mockMailSender);

        UserOperationEvent event = new UserOperationEvent("user123", "created", "user@example.com");
        emailService.sendNotificationEmail(event);

        verify(mockMailSender).send((MimeMessage) any()); // проверяем, что метод send действительно вызван
    }

    @Test
    public void testSendNotificationEmailFailure() {
        MockitoAnnotations.openMocks(this);
        doThrow(new MailSendException("")).when(mockMailSender).send((MimeMessage) any());

        assertThrows(MailSendException.class, () -> {
            UserOperationEvent event = new UserOperationEvent("user123", "created", "user@example.com");
            emailService.sendNotificationEmail(event);
        });
    }
}