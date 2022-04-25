package com.stock.microservice.dto;

public class ResponseData {

	private CompanyDto data;
	private ResponseMessage message;

	public CompanyDto getData() {
		return data;
	}

	public void setData(CompanyDto data) {
		this.data = data;
	}

	public ResponseMessage getMessage() {
		return message;
	}

	public void setMessage(ResponseMessage message) {
		this.message = message;
	}
}
