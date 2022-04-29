package com.stock.microservice.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

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
		stock.setPrice(stockDto.getPrice());
		stock.setDate(new Date());
		stock.setTimeStamp(new Date().getTime());

		stockRepository.save(stock);

		applicationLog.info("Exiting addCompanyStock Service");
		return isSuccessful;
	}

	@Override
	public CompanyDto filterStocks(String companyCode, String startDate, String endDate) throws Exception {
		applicationLog.info("Entering filterStocks Service");

		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		Date startDateFormatted = dateFormatter.parse(startDate);
		Date endDateFormatted = dateFormatter.parse(endDate);

		Calendar c = Calendar.getInstance();
		c.setTime(endDateFormatted);
		c.add(Calendar.DATE, 1);
		endDateFormatted = c.getTime();

		List<StockDao> stocks = stockRepository.filterStock(companyCode, startDateFormatted, endDateFormatted);
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

			List<Double> stockPrices = new ArrayList<Double>();
			Double maxPrice = null;
			Double minPrice = null;

			List<StockDto> stockDtos = new ArrayList<StockDto>();
			for (StockDao stock : stocks) {
				StockDto stockDto = new StockDto();
				stockDto.setPrice(stock.getPrice());
				stockDto.setDate(stock.getDate());
				stockDto.setStockId(stock.getStockId());
				stockDto.setTimeStamp(stock.getTimeStamp());
				stockDtos.add(stockDto);

				if (minPrice == null || minPrice > stock.getPrice()) {
					minPrice = stock.getPrice();
				}
				if (maxPrice == null || maxPrice < stock.getPrice()) {
					maxPrice = stock.getPrice();
				}

				stockPrices.add(stock.getPrice());
			}
			
			OptionalDouble average = stockPrices.stream().mapToDouble(a -> a).average();
			companyDetails.setAvgStockPrice(average.isPresent() ? average.getAsDouble() : 0);
			companyDetails.setMinStockPrice(minPrice);
			companyDetails.setMaxStockPrice(maxPrice);
			
			companyDetails.setStocks(stockDtos);
		}

		applicationLog.info("Exiting filterStocks Service");
		return companyDetails;
	}

	@Override
	public Double fetchLatestStockPrice(String companyCode) throws Exception {
		applicationLog.info("Entering fetchLatestStockPrice Service");
		Double latestStock = null;
		Date latestTimestamp = null;
		List<StockDao> companyStocks = stockRepository.findByCompanyCode(companyCode);
		for (StockDao companyStock : companyStocks) {
			if (latestTimestamp == null || latestTimestamp.before(companyStock.getDate())) {
				latestTimestamp = companyStock.getDate();
				latestStock = companyStock.getPrice();
			}
		}
		applicationLog.info("Exiting fetchLatestStockPrice Service");
		return latestStock;
	}

	@Override
	public Boolean deleteCompanyStocks(String companyCode) throws Exception {
		applicationLog.info("Entering deleteCompanyStocks Service");
		Boolean isSuccessful = true;

		List<StockDao> companyStocks = stockRepository.findByCompanyCode(companyCode);
		for (StockDao companyStock : companyStocks) {
			stockRepository.delete(companyStock);
		}

		applicationLog.info("Exiting deleteCompanyStocks Service");
		return isSuccessful;
	}
}
