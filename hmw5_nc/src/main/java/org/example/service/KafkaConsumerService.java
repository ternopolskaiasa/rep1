package org.example.service;

import org.example.model.UserOperationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final EmailService emailService;

    @KafkaListener(topics = "${spring.kafka.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, UserOperationEvent> record) {
        log.info("Got an event {}", record.value().toString());
        UserOperationEvent event = record.value();
        try {
            emailService.sendNotificationEmail(event);
        } catch (Exception ex) {
            log.error("An error occurred while sending", ex);
        }
    }
}
