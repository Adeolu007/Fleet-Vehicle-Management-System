package com.adeolu.Notification.Management.Service.repository;

import com.adeolu.Notification.Management.Service.modal.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailEntity, String> {
}
