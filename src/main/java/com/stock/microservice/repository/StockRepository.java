package com.stock.microservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.stock.microservice.entity.StockDao;

public interface StockRepository extends MongoRepository<StockDao, String> {

	@Query("{'companyCode': ?0, 'startDate': ?1, 'endDate': ?2}")
	public List<StockDao> filterStock(String companyCode, String startDate, String endDate);
}
