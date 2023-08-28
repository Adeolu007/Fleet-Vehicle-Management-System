package com.adeolu.Notification.Management.Service.Rabbit;

import com.adeolu.Notification.Management.Service.dto.EmailDetails;
import com.adeolu.Notification.Management.Service.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQEmailConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "${rabbitmq.occupant.booking.queue}")
    public void sendUserRegistrationDetails(EmailDetails emailDetails) {
        emailService.sendSimpleMessage(emailDetails);
    }

}
