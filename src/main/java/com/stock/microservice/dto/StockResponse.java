package com.stock.microservice.dto;

public class StockResponse<D> {

	private ResponseMessage message;
	private D data;

	public ResponseMessage getMessage() {
		return this.message;
	}

	public StockResponse<D> withMessage(ResponseMessage message) {
		this.message = message;
		return this;
	}

	public StockResponse<D> withData(D data) {
		this.data = data;
		return this;
	}

	public D getData() {
		return data;
	}
}
