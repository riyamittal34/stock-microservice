package com.stock.microservice.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The Class StockDao.
 */
@Document("Stock")
public class StockDao {

	/** The stock id. */
	@Id
	private String stockId;

	/** The company code. */
	private String companyCode;

	/** The date. */
	private Date date;

	/** The price. */
	private Double price;

	/** The time stamp. */
	private Long timeStamp;

	/**
	 * Gets the stock id.
	 *
	 * @return the stock id
	 */
	public String getStockId() {
		return stockId;
	}

	/**
	 * Sets the stock id.
	 *
	 * @param stockId the new stock id
	 */
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the new company code
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * Gets the time stamp.
	 *
	 * @return the time stamp
	 */
	public Long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Sets the time stamp.
	 *
	 * @param timeStamp the new time stamp
	 */
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
}
