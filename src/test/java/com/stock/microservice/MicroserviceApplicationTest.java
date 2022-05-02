package com.stock.microservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * The Class MicroserviceApplicationTest.
 */
@SpringBootTest
class MicroserviceApplicationTest {

	/**
	 * Context loads.
	 */
	@Test
    public void contextLoads() {
		MicroserviceApplication.main(new String[] {});
	}
}
