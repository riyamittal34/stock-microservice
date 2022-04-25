package com.stock.microservice.dto;

import java.util.List;

public class CompanyDto {

	private String companyId;
	private String companyCode;
	private String companyName;
	private List<StockDto> stocks;

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

	public List<StockDto> getStocks() {
		return stocks;
	}

	public void setStocks(List<StockDto> stocks) {
		this.stocks = stocks;
	}

	@Override
	public String toString() {
		return "CompanyDto [companyId=" + companyId + ", companyCode=" + companyCode + ", companyName=" + companyName
				+ ", stocks=" + stocks + "]";
	}
}
