package com.stock.microservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class HomeController.
 */
@RestController
@RequestMapping("/stock")
public class HomeController {

	/**
	 * Index.
	 *
	 * @return the string
	 */
	@GetMapping(value = "/")
	public String index() {
		return "redirect:swagger-ui.html";

	}
}
