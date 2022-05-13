package com.stock.microservice;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
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

	/**
	 * Api.
	 *
	 * @return the grouped open api
	 */
	@Bean
	public GroupedOpenApi api() {
		return GroupedOpenApi.builder().group("StockController").packagesToScan("com.stock.microcservice").build();
	}
}
