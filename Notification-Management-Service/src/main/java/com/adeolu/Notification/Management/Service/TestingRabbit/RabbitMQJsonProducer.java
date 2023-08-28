package com.adeolu.Vehicle.Management.Service.Gradle.RabbitMQ;

import com.adeolu.Vehicle.Management.Service.Gradle.dto.EmailDetails;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.occupant.booking.key}")
    private String occupantBookingKey;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(EmailDetails emailDetails) {
        rabbitTemplate.convertAndSend(exchange, occupantBookingKey, emailDetails);
    }

}
