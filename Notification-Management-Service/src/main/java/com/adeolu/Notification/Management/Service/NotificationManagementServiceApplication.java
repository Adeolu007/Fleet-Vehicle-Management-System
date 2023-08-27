package com.adeolu.Notification.Management.Service;

import com.adeolu.Notification.Management.Service.dto.EmailDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class NotificationManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationManagementServiceApplication.class, args);
	}

}
