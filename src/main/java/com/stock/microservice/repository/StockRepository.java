package com.stock.microservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.stock.microservice.entity.StockDao;

/**
 * The Interface StockRepository.
 */
public interface StockRepository extends MongoRepository<StockDao, String> {

	/**
	 * Filter stock.
	 *
	 * @param companyCode the company code
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	@Query("{'companyCode': ?0, 'date': {$gte: ?1, $lte: ?2}}")
	public List<StockDao> filterStock(String companyCode, Date startDate, Date endDate);

	/**
	 * Find by company code.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	public List<StockDao> findByCompanyCode(String companyCode);
}
