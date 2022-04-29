package com.stock.microservice.dto;

import java.util.List;

public class CompanyDto {

	private String companyId;
	private String companyCode;
	private String companyName;
	private String companyCeo;
	private String companyTurnover;
	private String companyWebsite;
	private String stockExchange;
	private List<StockDto> stocks;
	private Double maxStockPrice;
	private Double minStockPrice;
	private Double avgStockPrice;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCeo() {
		return companyCeo;
	}

	public void setCompanyCeo(String companyCeo) {
		this.companyCeo = companyCeo;
	}

	public String getCompanyTurnover() {
		return companyTurnover;
	}

	public void setCompanyTurnover(String companyTurnover) {
		this.companyTurnover = companyTurnover;
	}

	public String getCompanyWebsite() {
		return companyWebsite;
	}

	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}

	public String getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}

	public List<StockDto> getStocks() {
		return stocks;
	}

	public void setStocks(List<StockDto> stocks) {
		this.stocks = stocks;
	}

	public Double getMaxStockPrice() {
		return maxStockPrice;
	}

	public void setMaxStockPrice(Double maxStockPrice) {
		this.maxStockPrice = maxStockPrice;
	}

	public Double getMinStockPrice() {
		return minStockPrice;
	}

	public void setMinStockPrice(Double minStockPrice) {
		this.minStockPrice = minStockPrice;
	}

	public Double getAvgStockPrice() {
		return avgStockPrice;
	}

	public void setAvgStockPrice(Double avgStockPrice) {
		this.avgStockPrice = avgStockPrice;
	}
}
