package com.stock.microservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.stock.microservice.dto.CompanyDto;
import com.stock.microservice.dto.ResponseData;
import com.stock.microservice.entity.StockDao;
import com.stock.microservice.repository.StockRepository;
import com.stock.microservice.serviceImpl.StockServiceImpl;
import com.stock.microservice.util.MockSample;

/**
 * The Class StockServiceTest.
 */
@SpringBootTest
@AutoConfigureMockMvc
class StockServiceTest {

	/** The stock service. */
	@InjectMocks
	StockServiceImpl stockService;

	/** The stock repository. */
	@Mock
	StockRepository stockRepository;

	/** The rest template. */
	@Mock
	RestTemplate restTemplate;

	/**
	 * Adds the company stock test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void addCompanyStockTest() throws Exception {

		when(stockRepository.save(any(StockDao.class))).thenReturn(MockSample.getStockDaoObject());
		Boolean isSuccess = stockService.addCompanyStock("abc", 210.50);

		assertTrue(isSuccess);
	}

	/**
	 * Filter stocks test.
	 *
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void filterStocksExceptionTest() throws Exception {
		List<StockDao> stocks = new ArrayList<StockDao>();
		stocks.add(MockSample.getStockDaoObject());
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date startDate = formatter.parse("12-01-2022");
		Date endDate = formatter.parse("22-01-2022");

		ResponseData responseData = new ResponseData();
		CompanyDto companyDto = MockSample.getCompanyObject();
		responseData.setData(companyDto);
		ResponseEntity<ResponseData> responseEntity = new ResponseEntity<>(responseData, HttpStatus.OK);

		when(stockRepository.filterStock("abc", startDate, endDate)).thenReturn(stocks);
		when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.any(HttpEntity.class), ArgumentMatchers.any(Class.class))).thenReturn(responseEntity);
		CompanyDto company = stockService.filterStocks(companyDto.getCompanyCode(), "12-01-2022", "21-01-2022");

		assertEquals(1, company.getStocks().size());
	}

	/**
	 * Filter stocks test.
	 *
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void filterStocksTest() throws Exception {
		List<StockDao> stocks = new ArrayList<StockDao>();
		stocks.add(MockSample.getStockDaoObject());
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date startDate = formatter.parse("12-01-2022");
		Date endDate = formatter.parse("22-01-2022");

		when(stockRepository.filterStock("abc", startDate, endDate)).thenReturn(stocks);
		when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.any(Class.class)))
				.thenThrow(RuntimeException.class);
		CompanyDto company = stockService.filterStocks("abc", "12-01-2022", "21-01-2022");

		assertNull(company);
	}

	/**
	 * Fetch latest stock price test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void fetchLatestStockPriceTest() throws Exception {
		List<StockDao> stocks = new ArrayList<StockDao>();
		stocks.add(MockSample.getStockDaoObject());

		when(stockRepository.findByCompanyCode("abc")).thenReturn(stocks);
		Double price = stockService.fetchLatestStockPrice("abc");

		assertEquals(200.50, price);
	}

	/**
	 * Delete company stocks test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void deleteCompanyStocksTest() throws Exception {
		List<StockDao> stocks = new ArrayList<StockDao>();
		stocks.add(MockSample.getStockDaoObject());

		when(stockRepository.findByCompanyCode("abc")).thenReturn(stocks);
		Boolean isSuccessful = stockService.deleteCompanyStocks("abc");

		assertTrue(isSuccessful);
	}

}
