package com.stock.microservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * The Class HomeControllerTest.
 */
@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	/** The home controller. */
	@InjectMocks
	HomeController homeController;

	/**
	 * Sets the up.
	 */
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Test get swagger status.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetSwaggerStatus() throws Exception {
		mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is3xxRedirection())
				.andReturn();
	}

}
