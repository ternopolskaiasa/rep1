package org.example;

import org.example.model.UserOperationEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.service.EmailService;
import org.example.service.KafkaConsumerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.Acknowledgment;

import static org.mockito.Mockito.*;

public class KafkaConsumerServiceTest {

    @Mock
    private Acknowledgment acknowledgment;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsumeMessage() {
        UserOperationEvent event = new UserOperationEvent("user123", "updated", "user@example.com");
        ConsumerRecord<String, UserOperationEvent> record = new ConsumerRecord<>("topic", 0, 0L, null, event);

        kafkaConsumerService.listen(record);

        verify(emailService).sendNotificationEmail(event);
        verify(acknowledgment).acknowledge();
    }
}
