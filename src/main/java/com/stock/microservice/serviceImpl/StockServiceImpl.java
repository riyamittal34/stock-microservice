package com.stock.microservice.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.microservice.dto.CompanyDto;
import com.stock.microservice.dto.ResponseData;
import com.stock.microservice.dto.StockDto;
import com.stock.microservice.entity.StockDao;
import com.stock.microservice.repository.StockRepository;
import com.stock.microservice.service.StockService;

@Service
public class StockServiceImpl implements StockService {

	private final Logger applicationLog = LoggerFactory.getLogger("[APPLICATION]");

	private final Logger errorLog = LoggerFactory.getLogger("[ERROR]");

	@Autowired
	StockRepository stockRepository;

	@Override
	public Boolean addCompanyStock(String companyCode, String requestBody) throws Exception {
		applicationLog.info("Entering addCompanyStock Service");
		Boolean isSuccessful = true;
		StockDto stockDto = null;
		try {
			stockDto = new ObjectMapper().readValue(requestBody, StockDto.class);
		} catch (Exception e) {
			errorLog.error("Error in mapping request body to stock. error: {}", e.getMessage());
			throw e;
		}

		StockDao stock = new StockDao();
		stock.setCompanyCode(companyCode);
		stock.setEndDate(stockDto.getEndDate());
		stock.setPrice(stockDto.getPrice());
		stock.setStartDate(stockDto.getStartDate());
		stock.setTimeStamp(new Timestamp(System.currentTimeMillis()).toString());

		stockRepository.save(stock);

		applicationLog.info("Exiting addCompanyStock Service");
		return isSuccessful;
	}

	@Override
	public CompanyDto filterStocks(String companyCode, String startDate, String endDate) throws Exception {
		applicationLog.info("Entering filterStocks Service");

		List<StockDao> stocks = stockRepository.filterStock(companyCode, startDate, endDate);
		String companyUrl = "http://localhost:8085/api/v1.0/market/company/info/" + companyCode;
		applicationLog.info("companyUrl: {}", companyUrl);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ResponseData> response = restTemplate.getForEntity(companyUrl, ResponseData.class);
		applicationLog.info("response: {}", response.getStatusCode());
		CompanyDto companyDetails = null;
		if (response.getStatusCode().is2xxSuccessful()) {
			applicationLog.info("responseBody: {}", response.getBody());
			ResponseData responseData = response.getBody();
			companyDetails = responseData.getData();
			
			List<StockDto> stockDtos = new ArrayList<StockDto>();
			for (StockDao stock : stocks) {
				StockDto stockDto = new StockDto();
				stockDto.setEndDate(stock.getEndDate());
				stockDto.setPrice(stock.getPrice());
				stockDto.setStartDate(stock.getStartDate());
				stockDto.setStockId(stock.getStockId());
				stockDto.setTimeStamp(stock.getTimeStamp());
				stockDtos.add(stockDto);
			}
			
			companyDetails.setStocks(stockDtos);
		}

		applicationLog.info("Exiting filterStocks Service");
		return companyDetails;
	}
}
