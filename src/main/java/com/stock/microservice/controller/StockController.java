package com.stock.microservice.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stock.microservice.dto.CompanyDto;
import com.stock.microservice.dto.ResponseMessage;
import com.stock.microservice.dto.StockResponse;
import com.stock.microservice.service.StockService;

@Controller
@RequestMapping("/api/v1.0/market/stock")
public class StockController {
	
	private final Logger applicationLog = LoggerFactory.getLogger("[APPLICATION]");

	private final Logger errorLog = LoggerFactory.getLogger("[ERROR]");
	
	@Autowired
	StockService stockService;

	@PostMapping(value = "/add/{companycode}")
	public ResponseEntity<StockResponse<Boolean>> addCompanyStock(@PathVariable("companycode") String companyCode,
			@RequestBody String requestBody) {
		applicationLog.info("Entering registerCompany Controller");
		StockResponse<Boolean> response = new StockResponse<>();
		ResponseMessage message = new ResponseMessage();
		try {
			Boolean isSuccessful = stockService.addCompanyStock(companyCode, requestBody);
			response.withData(isSuccessful);
			applicationLog.info("Exiting registerCompany Controller");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			errorLog.error("Error in adding Stocks. Error: {}", e.getMessage());
			message.setCode("STOCK_ADD_FAILED");
			message.setDescription("Error in adding stock");
			response.withData(false);
			response.withMessage(message);
			applicationLog.info("Exiting registerCompany Controller");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/get/{companycode}/{startdate}/{enddate}")
	public ResponseEntity<StockResponse<CompanyDto>> filterStocks(@PathVariable("companycode") String companyCode,
			@PathVariable("startdate") String startDate, @PathVariable("enddate") String endDate) {
		applicationLog.info("Entering filterStocks Controller");
		StockResponse<CompanyDto> response = new StockResponse<>();
		ResponseMessage message = new ResponseMessage();
		try {
			CompanyDto company = stockService.filterStocks(companyCode, startDate, endDate);
			if(company != null && company.getStocks().size() > 0) {
				message.setCode("FILTER_STOCK_SUCESS");
				message.setDescription("Stocks Fetched");
				response.withData(company);
				response.withMessage(message);
				applicationLog.info("Exiting filterStocks Controller");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				message.setCode("NO_STOCK_FOUND");
				message.setDescription("No Stock found");
				response.withData(null);
				response.withMessage(message);
				applicationLog.info("Exiting filterStocks Controller");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			errorLog.error("Error in filtering stocks. Error: {}", e.getMessage());
			message.setCode("FILTER_STOCK_FAILED");
			message.setDescription("Error in filtering stock");
			response.withData(null);
			response.withMessage(message);
			applicationLog.info("Exiting filterStocks Controller");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}