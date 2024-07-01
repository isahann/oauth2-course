package com.isahann.sampleapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SampleApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleApiGatewayApplication.class, args);
	}

}
