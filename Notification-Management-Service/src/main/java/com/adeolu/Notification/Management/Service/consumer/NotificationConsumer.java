package com.adeolu.Notification.Management.Service.consumer;

import com.adeolu.Notification.Management.Service.dto.EmailDetails;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

public class NotificationConsumer {
    @KafkaListener(topics = "your-topic")
    public void consumeMessage(ConsumerRecord<String, EmailDetails> emailDetailsConsumerRecord) {
        try {
            // Process the message
        } catch (Exception e) {
            // Handle the exception (e.g., log it)
        }
    }

}
