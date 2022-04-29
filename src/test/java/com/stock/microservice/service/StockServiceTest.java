package com.stock.microservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.stock.microservice.dto.CompanyDto;
import com.stock.microservice.dto.ResponseData;
import com.stock.microservice.dto.StockDto;
import com.stock.microservice.entity.StockDao;
import com.stock.microservice.repository.StockRepository;
import com.stock.microservice.serviceImpl.StockServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
class StockServiceTest {

	@InjectMocks
	StockServiceImpl stockService;

	@Mock
	StockRepository stockRepository;
	
	@MockBean
	RestTemplate restTemplate;

	@Test
	public void addCompanyStockTest() throws Exception {

		when(stockRepository.save(any(StockDao.class))).thenReturn(getStockDaoObject());
		Boolean isSuccess = stockService.addCompanyStock("abc",
				"{\"price\": 210.50}");

		assertTrue(isSuccess);
	}

	@Test
	public void addCompanyStockMappingExceptionTest() throws Exception {
		Assertions.assertThrows(Exception.class, () -> {
			stockService.addCompanyStock("abc", "TestData");
		});
	}
	
	@Test
	public void filterStocksTest() throws Exception {
		List<StockDao> stocks = new ArrayList<StockDao>();
		stocks.add(getStockDaoObject());
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date startDate = formatter.parse("12-01-2022");
		Date endDate = formatter.parse("22-01-2022");
		
		String companyInfoUrl = "http://localhost:8085/api/v1.0/market/company/info/abc";
		ResponseData responseData = new ResponseData();
		responseData.setData(getCompanyObject());
		ResponseEntity<ResponseData> responseEntity = new ResponseEntity<>(responseData, HttpStatus.OK); 
		
		when(stockRepository.filterStock("abc", startDate, endDate)).thenReturn(stocks);
		when(restTemplate.getForEntity(companyInfoUrl, ResponseData.class)).thenReturn(responseEntity);
		CompanyDto company = stockService.filterStocks("abc", "12-01-2022", "21-01-2022");
		
		assertEquals(1, company.getStocks().size());
	}
	
	@Test
	public void fetchLatestStockPriceTest() throws Exception {
		List<StockDao> stocks = new ArrayList<StockDao>();
		stocks.add(getStockDaoObject());
		
		when(stockRepository.findByCompanyCode("abc")).thenReturn(stocks);
		Double price = stockService.fetchLatestStockPrice("abc");
		
		assertEquals(200.50, price);
	}
	
	@Test
	public void deleteCompanyStocksTest() throws Exception {
		List<StockDao> stocks = new ArrayList<StockDao>();
		stocks.add(getStockDaoObject());
		
		when(stockRepository.findByCompanyCode("abc")).thenReturn(stocks);
		Boolean isSuccessful = stockService.deleteCompanyStocks("abc");
		
		assertTrue(isSuccessful);
	}

	private StockDto getStockObject() {
		StockDto stock = new StockDto();
		stock.setPrice(200.50);
		stock.setStockId(UUID.randomUUID().toString());
		stock.setDate(new Date());
		stock.setTimeStamp(new Date().getTime());
		return stock;
	}

	private StockDao getStockDaoObject() {
		StockDao stock = new StockDao();
		stock.setPrice(200.50);
		stock.setStockId(UUID.randomUUID().toString());
		stock.setDate(new Date());
		stock.setTimeStamp(new Date().getTime());
		return stock;
	}

	private CompanyDto getCompanyObject() {
		CompanyDto company = new CompanyDto();
		company.setCompanyCode("abc");
		company.setCompanyId(UUID.randomUUID().toString());
		company.setCompanyName("ABC Company");
		company.setCompanyTurnover("10000000000");
		company.setCompanyWebsite("http://www.google.com");
		company.setCompanyCeo("Riya Mittal");
		company.setStockExchange("NSE");

		List<StockDto> stocks = new ArrayList<StockDto>();
		stocks.add(getStockObject());
		company.setStocks(stocks);
		return company;
	}

}
