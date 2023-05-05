package com.devflow.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DevflowGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevflowGatewayApplication.class, args);
	}

}
