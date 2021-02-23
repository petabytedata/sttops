package com.favoritemedium.sttops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Entry point of the application
 */
@EnableCircuitBreaker
@EnableHystrixDashboard
@SpringBootApplication
public class SttOpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SttOpsApplication.class, args);
	}

}
