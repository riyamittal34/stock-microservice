package com.stock.microservice.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.stock.microservice.dto.CompanyDto;
import com.stock.microservice.dto.ResponseData;
import com.stock.microservice.dto.StockDto;
import com.stock.microservice.entity.StockDao;
import com.stock.microservice.repository.StockRepository;
import com.stock.microservice.service.StockService;

/**
 * The Class StockServiceImpl.
 */
@Service
public class StockServiceImpl implements StockService {

	/** The application log. */
	private final Logger applicationLog = LoggerFactory.getLogger("[APPLICATION]");

	/** The stock repository. */
	@Autowired
	StockRepository stockRepository;

	/** The rest template. */
	@Autowired
	RestTemplate restTemplate;

	/**
	 * Adds the company stock.
	 *
	 * @param companyCode the company code
	 * @param stockPrice the stock price
	 * @return the boolean
	 * @throws Exception the exception
	 */
	@Override
	public Boolean addCompanyStock(String companyCode, Double stockPrice) throws Exception {
		applicationLog.info("Entering addCompanyStock Service");
		Boolean isSuccessful = true;

		StockDao stock = new StockDao();
		stock.setCompanyCode(companyCode);
		stock.setPrice(stockPrice);
		stock.setDate(new Date());
		stock.setTimeStamp(new Date().getTime());

		stockRepository.save(stock);

		applicationLog.info("Exiting addCompanyStock Service");
		return isSuccessful;
	}

	/**
	 * Filter stocks.
	 *
	 * @param companyCode the company code
	 * @param startDate   the start date
	 * @param endDate     the end date
	 * @return the company dto
	 * @throws Exception the exception
	 */
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
		String companyUrl = "http://company-service/api/v1.0/market/company/info/" + companyCode;
		applicationLog.info("companyUrl: {}", companyUrl);

		CompanyDto companyDetails = null;
		ResponseEntity<ResponseData> response = null;
		try {
			response = restTemplate.exchange(companyUrl, HttpMethod.GET, getAuthToken(), ResponseData.class);
			applicationLog.info("response: {}", response.getStatusCode());
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
		} catch (Exception e) {
			applicationLog.error("Error in finding company with company code: {}", companyCode);
		}
		applicationLog.info("Exiting filterStocks Service");
		return companyDetails;
	}

	/**
	 * Fetch latest stock price.
	 *
	 * @param companyCode the company code
	 * @return the double
	 * @throws Exception the exception
	 */
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

	/**
	 * Delete company stocks.
	 *
	 * @param companyCode the company code
	 * @return the boolean
	 * @throws Exception the exception
	 */
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HttpEntity<String> getAuthToken() {
		String token = null;
		
		String url = "http://company-service/api/v1.0/market/company/login";
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("username", "riya");
		body.add("password", "riya@123");
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(body, header);
		try {
			ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
			Map<String, String> responseBody = (Map<String, String>) response.getBody();
			token = responseBody.get("access_token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		HttpEntity<String> getEntity = new HttpEntity<>(headers);
		return getEntity;
	}
}
