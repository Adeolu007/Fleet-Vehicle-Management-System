package com.adeolu.Fuel.Management.Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FuelManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuelManagementServiceApplication.class, args);
	}

}
