//package com.adeolu.Notification.Management.Service.config;
//
//import com.adeolu.Notification.Management.Service.dto.EmailDetails;
//import com.adeolu.Notification.Management.Service.service.EmailService;
//import com.adeolu.Notification.Management.Service.service.serviceImpl.EmailServiceImpl;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
////import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
////@AllArgsConstructor
//@Slf4j
//public class RabbitMQConsumer {
//
//    private final EmailServiceImpl emailService;
//
//    @Autowired
//    public RabbitMQConsumer(EmailServiceImpl emailService) {
//        this.emailService = emailService;
//    }
//
//
//    @RabbitListener(queues = "${rabbitmq.registration.email.queue.name}")
//    public void receiveRegistrationEmail(EmailDetails emailDetails) {
//        // Handle the registration email message using the emailService
//        String result = emailService.sendSimpleMessage(emailDetails);
//        System.out.println("Received registration email: " + emailDetails);
//        System.out.println("Email Service Result: " + result);
//    }
////
////    @RabbitListener(queues = "${rabbitmq.login.email.queue.name}")
////    public void receiveLoginEmail(EmailDetails emailDetails) {
////        // Handle the login email message using the emailService
////        String result = emailService.sendSimpleMessage(emailDetails);
////        System.out.println("Received login email: " + emailDetails);
////        System.out.println("Email Service Result: " + result);
////    }
////
////    @RabbitListener(queues = "${rabbitmq.credit.email.queue.name}")
////    public void receiveCreditEmail(EmailDetails emailDetails) {
////        // Handle the credit email message using the emailService
////        String result = emailService.sendMessageWithAttachment(emailDetails);
////        System.out.println("Received credit email: " + emailDetails);
////        System.out.println("Email Service Result: " + result);
////    }
////
////    @RabbitListener(queues = "${rabbitmq.debit.email.queue.name}")
////    public void receiveDebitEmail(EmailDetails emailDetails) {
////        // Handle the debit email message using the emailService
////        String result = emailService.sendMessageWithAttachment(emailDetails);
////        System.out.println("Received debit email: " + emailDetails);
////        System.out.println("Email Service Result: " + result);
////    }
//
////    private EmailService emailService;
//
////    @RabbitListener(queues = "${rabbitmq.registration.email.queue.name}")
////    public void sendRegistrationEmailNotification(EmailDetails emailDetails) {
////        try {
////            emailService.sendSimpleMessage(emailDetails);
////            log.info("Message sent -> " + emailDetails);
////        } catch (Exception e) {
////            log.error("Error sending email: " + e.getMessage(), e);
////        }
////    }
////
////    @RabbitListener(queues = "${rabbitmq.login.email.queue.name}")
////    public void sendLoginEmailNotification(EmailDetails emailDetails) {
////        try{        emailService.sendSimpleMessage(emailDetails);
////            log.info(String.format("Message sent -> %s" + emailDetails));
////
////        }catch (Exception e) {
////            log.error("Error sending email: " + e.getMessage(), e);
////        }
//
//    }
//
////    @RabbitListener(queues = "${rabbitmq.credit.email.queue.name}")
////    public void sendCreditEmailNotification(EmailDetails emailDetails){
////        emailService.sendSimpleMessage(emailDetails);
////        log.info(String.format("Message sent -> %s", emailDetails));
////    }
////    @RabbitListener(queues = "${rabbitmq.debit.email.queue.name}")
////    public void sendDebitEmailNotification(EmailDetails emailDetails){
////        emailService.sendSimpleMessage(emailDetails);
////        log.info(String.format("Message sent -> %s", emailDetails));
////    }
////}