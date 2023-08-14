package com.adeolu.Driver.Management.Service.Gradle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DriverManagementServiceGradleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriverManagementServiceGradleApplication.class, args);
	}

}
