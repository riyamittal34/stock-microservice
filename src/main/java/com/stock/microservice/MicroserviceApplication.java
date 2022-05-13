package com.stock.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

/**
 * The Class MicroserviceApplication.
 */
@SpringBootApplication
@EnableMongoRepositories
@EnableEurekaClient
@OpenAPIDefinition
public class MicroserviceApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(MicroserviceApplication.class, args);
	}
}
