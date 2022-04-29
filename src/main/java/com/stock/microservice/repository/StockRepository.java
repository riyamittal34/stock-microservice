package com.stock.microservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.stock.microservice.entity.StockDao;

public interface StockRepository extends MongoRepository<StockDao, String> {

	@Query("{'companyCode': ?0, 'date': {$gte: ?1}, 'date': {$lte: ?2}}")
	public List<StockDao> filterStock(String companyCode, Date startDate, Date endDate);

	public List<StockDao> findByCompanyCode(String companyCode);
}
