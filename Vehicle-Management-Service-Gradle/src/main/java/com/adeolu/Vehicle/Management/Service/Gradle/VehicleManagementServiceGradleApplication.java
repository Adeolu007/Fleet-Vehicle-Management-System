package com.adeolu.Vehicle.Management.Service.Gradle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VehicleManagementServiceGradleApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleManagementServiceGradleApplication.class, args);
	}

}
