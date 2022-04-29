package com.stock.microservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.stock.microservice.dto.CompanyDto;
import com.stock.microservice.dto.ResponseData;
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
	
	@Mock
	RestTemplate restTemplate;

	@Test
	public void addCompanyStockTest() throws Exception {

		when(stockRepository.save(any(StockDao.class))).thenReturn(getStockDaoObject());
		Boolean isSuccess = stockService.addCompanyStock("abc",
				"{\"startDate\": \"12-01-2022\", \"endDate\": \"21-01-2022\", \"price\": 210.50}");

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
		when(stockRepository.filterStock("abc", "12-01-2022", "21-01-2022")).thenReturn(stocks);
		when(restTemplate.getForEntity(anyString(), ResponseData.class)).thenReturn(null);
		CompanyDto company = stockService.filterStocks("abc", "12-01-2022", "21-01-2022");
		
		assertEquals(1, company.getStocks().size());
	}

//	private StockDto getStockObject() {
//		StockDto stock = new StockDto();
//		stock.setEndDate("21-01-2022");
//		stock.setPrice(200.50);
//		stock.setStockId(UUID.randomUUID().toString());
//		stock.setStartDate("12-01-2022");
//		stock.setTimeStamp(new Timestamp(System.currentTimeMillis()).toString());
//		return stock;
//	}

	private StockDao getStockDaoObject() {
		StockDao stock = new StockDao();
		stock.setEndDate("21-01-2022");
		stock.setPrice(200.50);
		stock.setStockId(UUID.randomUUID().toString());
		stock.setStartDate("12-01-2022");
		stock.setTimeStamp(new Timestamp(System.currentTimeMillis()).toString());
		return stock;
	}

//	private CompanyDto getCompanyObject() {
//		CompanyDto company = new CompanyDto();
//		company.setCompanyCode("abc");
//		company.setCompanyId(UUID.randomUUID().toString());
//		company.setCompanyName("ABC Company");
//
//		List<StockDto> stocks = new ArrayList<StockDto>();
//		stocks.add(getStockObject());
//		company.setStocks(stocks);
//		return company;
//	}

}
