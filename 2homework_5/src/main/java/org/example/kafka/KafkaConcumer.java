package org.example.kafka;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "your-topic", groupId = "group-id")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}