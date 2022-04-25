package com.stock.microservice.service;

import java.util.Date;

import com.stock.microservice.dto.CompanyDto;

public interface StockService {

	public Boolean addCompanyStock(String companyCode, String requestBody) throws Exception;

	public CompanyDto filterStocks(String companyCode, String startDate, String endDate) throws Exception;
}
