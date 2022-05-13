package com.stock.microservice.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.stock.microservice.dto.CompanyDto;
import com.stock.microservice.dto.StockDto;
import com.stock.microservice.service.StockService;

/**
 * The Class StockControllerTest.
 */
@SpringBootTest
@AutoConfigureMockMvc
class StockControllerTest {

	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	/** The stock service. */
	@MockBean
	StockService stockService;

	/**
	 * Adds the company stock test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void addCompanyStockTest() throws Exception {

		when(stockService.addCompanyStock(ArgumentMatchers.anyString(), ArgumentMatchers.anyDouble())).thenReturn(true);
		this.mockMvc
				.perform(post("/api/v1.0/market/stock/add/abc").content("210.50")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("true")))
				.andReturn();
	}

	/**
	 * Adds the company stock exception test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void addCompanyStockExceptionTest() throws Exception {

		when(stockService.addCompanyStock(ArgumentMatchers.anyString(), ArgumentMatchers.anyDouble()))
				.thenThrow(Exception.class);
		this.mockMvc
				.perform(post("/api/v1.0/market/stock/add/abc").content("210.50")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isInternalServerError())
				.andExpect(content().string(containsString("STOCK_ADD_FAILED"))).andReturn();
	}

	/**
	 * Filter stocks test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void filterStocksTest() throws Exception {

		when(stockService.filterStocks("abc", "12-01-2022", "21-01-2022")).thenReturn(getCompanyObject());
		this.mockMvc.perform(get("/api/v1.0/market/stock/get/abc/12-01-2022/21-01-2022")).andDo(print())
				.andExpect(status().isOk()).andExpect(content().string(containsString("FILTER_STOCK_SUCCESS")))
				.andReturn();
	}

	/**
	 * Filter stocks null data test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void filterStocksNullDataTest() throws Exception {

		when(stockService.filterStocks("abc", "12-01-2022", "21-01-2022")).thenReturn(null);
		this.mockMvc.perform(get("/api/v1.0/market/stock/get/abc/12-01-2022/21-01-2022")).andDo(print())
				.andExpect(status().isNotFound()).andExpect(content().string(containsString("NO_STOCK_FOUND")))
				.andReturn();
	}

	/**
	 * Filter stocks exception test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void filterStocksExceptionTest() throws Exception {

		when(stockService.filterStocks("abc", "12-01-2022", "21-01-2022")).thenThrow(Exception.class);
		this.mockMvc.perform(get("/api/v1.0/market/stock/get/abc/12-01-2022/21-01-2022")).andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(content().string(containsString("FILTER_STOCK_FAILED"))).andReturn();
	}

	/**
	 * Gets the latest stock price test.
	 *
	 * @return the latest stock price test
	 * @throws Exception the exception
	 */
	@Test
	void getLatestStockPriceTest() throws Exception {

		when(stockService.fetchLatestStockPrice("abc")).thenReturn(300.0);
		this.mockMvc.perform(get("/api/v1.0/market/stock/get/stockPrice/abc")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("LATEST_STOCK_PRICE_FETCHED"))).andReturn();
	}

	/**
	 * Gets the latest stock price null data test.
	 *
	 * @return the latest stock price null data test
	 * @throws Exception the exception
	 */
	@Test
	void getLatestStockPriceNullDataTest() throws Exception {

		when(stockService.fetchLatestStockPrice("abc")).thenReturn(null);
		this.mockMvc.perform(get("/api/v1.0/market/stock/get/stockPrice/abc")).andDo(print())
				.andExpect(status().isNotFound()).andExpect(content().string(containsString("NO_STOCK_FOUND")))
				.andReturn();
	}

	/**
	 * Gets the latest stock price exception test.
	 *
	 * @return the latest stock price exception test
	 * @throws Exception the exception
	 */
	@Test
	void getLatestStockPriceExceptionTest() throws Exception {

		when(stockService.fetchLatestStockPrice("abc")).thenThrow(Exception.class);
		this.mockMvc.perform(get("/api/v1.0/market/stock/get/stockPrice/abc")).andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(content().string(containsString("LATEST_STOCK_FETCH_FAILED"))).andReturn();
	}

	/**
	 * Delete company stocks test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void deleteCompanyStocksTest() throws Exception {

		when(stockService.deleteCompanyStocks("abc")).thenReturn(true);
		this.mockMvc.perform(delete("/api/v1.0/market/stock/delete/abc")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("COMPANY_STOCK_DELETED"))).andReturn();
	}

	/**
	 * Delete company stocks exception test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void deleteCompanyStocksExceptionTest() throws Exception {

		when(stockService.deleteCompanyStocks("abc")).thenThrow(Exception.class);
		this.mockMvc.perform(delete("/api/v1.0/market/stock/delete/abc")).andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(content().string(containsString("COMPANY_STOCK_DELETE_FAILED"))).andReturn();
	}

	/**
	 * Gets the stock object.
	 *
	 * @return the stock object
	 */
	private StockDto getStockObject() {
		StockDto stock = new StockDto();
		stock.setPrice(200.50);
		stock.setStockId(UUID.randomUUID().toString());
		stock.setDate(new Date());
		stock.setTimeStamp(new Date().getTime());
		return stock;
	}

	/**
	 * Gets the company object.
	 *
	 * @return the company object
	 */
	private CompanyDto getCompanyObject() {
		CompanyDto company = new CompanyDto();
		company.setCompanyCode("abc");
		company.setCompanyId(UUID.randomUUID().toString());
		company.setCompanyName("ABC Company");

		List<StockDto> stocks = new ArrayList<StockDto>();
		stocks.add(getStockObject());
		company.setStocks(stocks);
		return company;
	}
}
