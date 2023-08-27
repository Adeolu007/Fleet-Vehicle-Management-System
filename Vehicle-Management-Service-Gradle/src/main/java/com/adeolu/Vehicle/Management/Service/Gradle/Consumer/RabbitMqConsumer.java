//package com.adeolu.Vehicle.Management.Service.Gradle.Consumer;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class RabbitMqConsumer {
//
//    @RabbitListener(queues = { "${rabbitmq.queue.name}"})
//    public void consume (String message){
//    log.info("Received message -> %s", message);
//    }
//}
