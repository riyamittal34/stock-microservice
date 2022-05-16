package com.stock.microservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.stock.microservice.entity.RoleDao;

@Repository
public interface RoleRepository extends MongoRepository<RoleDao, String> {

	@Query("{'roleName': ?0}")
	RoleDao findByRoleName(String roleName);
}
