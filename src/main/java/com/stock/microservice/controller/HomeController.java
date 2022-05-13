package com.stock.microservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The Class HomeController.
 */
@Controller
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
