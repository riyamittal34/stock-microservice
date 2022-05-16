package com.stock.microservice.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.stock.microservice.service.StockService;
import com.stock.microservice.util.MockSample;

/**
 * The Class StockControllerTest.
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
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
	@WithMockUser
	void addCompanyStockTest() throws Exception {

		when(stockService.addCompanyStock(ArgumentMatchers.anyString(), ArgumentMatchers.anyDouble())).thenReturn(true);
		this.mockMvc
				.perform(post("/add/abc").content("210.50")
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
	@WithMockUser
	void addCompanyStockExceptionTest() throws Exception {

		when(stockService.addCompanyStock(ArgumentMatchers.anyString(), ArgumentMatchers.anyDouble()))
				.thenThrow(Exception.class);
		this.mockMvc
				.perform(post("/add/abc").content("210.50")
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
	@WithMockUser
	void filterStocksTest() throws Exception {

		when(stockService.filterStocks("abc", "12-01-2022", "21-01-2022")).thenReturn(MockSample.getCompanyObject());
		this.mockMvc.perform(get("/get/abc/12-01-2022/21-01-2022")).andDo(print())
				.andExpect(status().isOk()).andExpect(content().string(containsString("FILTER_STOCK_SUCCESS")))
				.andReturn();
	}

	/**
	 * Filter stocks null data test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@WithMockUser
	void filterStocksNullDataTest() throws Exception {

		when(stockService.filterStocks("abc", "12-01-2022", "21-01-2022")).thenReturn(null);
		this.mockMvc.perform(get("/get/abc/12-01-2022/21-01-2022")).andDo(print())
				.andExpect(status().isNotFound()).andExpect(content().string(containsString("NO_STOCK_FOUND")))
				.andReturn();
	}

	/**
	 * Filter stocks exception test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@WithMockUser
	void filterStocksExceptionTest() throws Exception {

		when(stockService.filterStocks("abc", "12-01-2022", "21-01-2022")).thenThrow(Exception.class);
		this.mockMvc.perform(get("/get/abc/12-01-2022/21-01-2022")).andDo(print())
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
	@WithMockUser
	void getLatestStockPriceTest() throws Exception {

		when(stockService.fetchLatestStockPrice("abc")).thenReturn(300.0);
		this.mockMvc.perform(get("/get/stockPrice/abc")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("LATEST_STOCK_PRICE_FETCHED"))).andReturn();
	}

	/**
	 * Gets the latest stock price null data test.
	 *
	 * @return the latest stock price null data test
	 * @throws Exception the exception
	 */
	@Test
	@WithMockUser
	void getLatestStockPriceNullDataTest() throws Exception {

		when(stockService.fetchLatestStockPrice("abc")).thenReturn(null);
		this.mockMvc.perform(get("/get/stockPrice/abc")).andDo(print())
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
	@WithMockUser
	void getLatestStockPriceExceptionTest() throws Exception {

		when(stockService.fetchLatestStockPrice("abc")).thenThrow(Exception.class);
		this.mockMvc.perform(get("/get/stockPrice/abc")).andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(content().string(containsString("LATEST_STOCK_FETCH_FAILED"))).andReturn();
	}

	/**
	 * Delete company stocks test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@WithMockUser
	void deleteCompanyStocksTest() throws Exception {

		when(stockService.deleteCompanyStocks("abc")).thenReturn(true);
		this.mockMvc.perform(delete("/delete/abc")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("COMPANY_STOCK_DELETED"))).andReturn();
	}

	/**
	 * Delete company stocks exception test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@WithMockUser
	void deleteCompanyStocksExceptionTest() throws Exception {

		when(stockService.deleteCompanyStocks("abc")).thenThrow(Exception.class);
		this.mockMvc.perform(delete("/delete/abc")).andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(content().string(containsString("COMPANY_STOCK_DELETE_FAILED"))).andReturn();
	}
}
