package com.adeolu.Notification.Management.Service.service.serviceImpl;

import com.adeolu.Notification.Management.Service.dto.EmailDetails;
import com.adeolu.Notification.Management.Service.modal.EmailEntity;
import com.adeolu.Notification.Management.Service.repository.EmailRepository;
import com.adeolu.Notification.Management.Service.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    private EmailRepository emailRepository;
    private JavaMailSender javaMailSender;
//    private ModelMapper modelMapper;

    @Value("${spring.mail.username}")
    private String mailSender;


    public EmailServiceImpl(EmailRepository emailRepository, JavaMailSender javaMailSender) {
        this.emailRepository = emailRepository;
        this.javaMailSender = javaMailSender;
    }

//    @KafkaListener(topics = "notificationTopic")
//    public void handleNotification(EmailDetails emailDetails) {
//        // send out an email notification
//        log.info("Received Notification for Order - {}", emailDetails);
//        log.info(emailDetails.getRecipient());
//        log.info(emailDetails.getMessage());
//    }

    @Override
  //  @Transactional
    @KafkaListener(topics = "notificationTopic")
//    public String sendSimpleMessage(EmailDetails emailDetails) {
//
//        try {
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setFrom(mailSender);
//            mailMessage.setTo(emailDetails.getRecipient());
//            mailMessage.setSubject(emailDetails.getSubject());
//            mailMessage.setText(emailDetails.getMessage());
//
//            javaMailSender.send(mailMessage);
//            log.info("Received Notification for Order - {}", emailDetails);
//            log.info(emailDetails.getRecipient());
//            log.info(emailDetails.getMessage());
//
//            EmailEntity email = EmailEntity.builder()
//                    .message(mailMessage.getText())
//                    .subject(mailMessage.getSubject())
//                    .attachment(null)
//                    .build();
//
//            emailRepository.save(email);
//
//            log.info(email.toString());
//            log.info("Message sent successfully");
//            return "Mail sent successfully";
//        } catch (MailException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public String sendSimpleMessage(EmailDetails emailDetails) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(mailSender);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setSubject(emailDetails.getSubject());
            mailMessage.setText(emailDetails.getMessage());

            javaMailSender.send(mailMessage);

            log.info("Received Notification for Order - {}", emailDetails);
            log.info("Recipient: " + emailDetails.getRecipient());
            log.info("Message: " + emailDetails.getMessage());

            EmailEntity email = EmailEntity.builder()
                    .message(mailMessage.getText())
                    .subject(mailMessage.getSubject())
                    .attachment(null)
                    .build();

            emailRepository.save(email);

            log.info(email.toString());
            log.info("Message sent successfully");
            return "Mail sent successfully";
        } catch (MailException e) {
            // Handle the mail sending exception here
            log.error("Error sending email: " + e.getMessage(), e);
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        } catch (Exception ex) {
            // Handle any other unexpected exceptions here
            log.error("Unexpected error: " + ex.getMessage(), ex);
            throw new RuntimeException("Unexpected error occurred: " + ex.getMessage(), ex);
        }
    }
    @Override
 //   @Transactional
    public String sendMessageWithAttachment(EmailDetails emailDetails) {
        try {
            //first tap into javaMailSender.createMimeMessage() to create Mime Message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            // tap into  MimeMessageHelper.
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mailSender);
            mimeMessageHelper.setTo(emailDetails.getRecipient());
            mimeMessageHelper.setSubject(emailDetails.getSubject());
            mimeMessageHelper.setText(emailDetails.getMessage());

            //to handle attachment
            FileSystemResource fileSystemResource = new FileSystemResource(new File(emailDetails.getAttachment()));
            mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

            //tap into javaMailSender to send message
            javaMailSender.send(mimeMessage);
            return "Mail sent successfully";

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
