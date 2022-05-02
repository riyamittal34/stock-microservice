package com.stock.microservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * The Class Config.
 */
@Configuration
public class Config {

	/**
	 * Gets the rest template.
	 *
	 * @return the rest template
	 */
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
