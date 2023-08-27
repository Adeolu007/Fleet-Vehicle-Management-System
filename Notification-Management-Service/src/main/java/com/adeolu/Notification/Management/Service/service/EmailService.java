package com.adeolu.Notification.Management.Service.service;

import com.adeolu.Notification.Management.Service.dto.EmailDetails;

;

public interface EmailService {
    String sendSimpleMessage(EmailDetails emailDetails);

    String sendMessageWithAttachment(EmailDetails emailDetails);
}
