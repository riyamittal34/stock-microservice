package com.stock.microservice.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.stock.microservice.dto.CompanyDto;
import com.stock.microservice.dto.RoleDto;
import com.stock.microservice.dto.StockDto;
import com.stock.microservice.dto.UserDto;
import com.stock.microservice.entity.RoleDao;
import com.stock.microservice.entity.StockDao;
import com.stock.microservice.entity.UserDao;

/**
 * The Class MockSample.
 */
public class MockSample {
	
	/**
	 * Gets the user dto.
	 *
	 * @return the user dto
	 */
	public static UserDto getUserDto() {
		UserDto user = new UserDto();
		user.setName("riya");
		user.setPassword("riya");
		user.setUsername("riya");
		return user;
	}
	
	/**
	 * Gets the user dao.
	 *
	 * @return the user dao
	 */
	public static UserDao getUserDao() {
		UserDao user = new UserDao();
		user.setName("riya");
		user.setPassword("riya");
		user.setUsername("riya");
		user.setUserId(UUID.randomUUID().toString());
		RoleDao role = new RoleDao();
		role.setRoleId(UUID.randomUUID().toString());
		role.setRoleName("ROLE_ADMIN");
		List<RoleDao> roles = new ArrayList<>();
		roles.add(role);
		user.setRoles(roles);
		return user;
	}
	
	/**
	 * Gets the role dto.
	 *
	 * @return the role dto
	 */
	public static RoleDto getRoleDto() {
		RoleDto role = new RoleDto();
		role.setRoleName("ROLE_ADMIN");
		return role;
	}
	
	/**
	 * Gets the role dao.
	 *
	 * @return the role dao
	 */
	public static RoleDao getRoleDao() {
		RoleDao role = new RoleDao();
		role.setRoleName("ROLE_ADMIN");
		role.setRoleId(UUID.randomUUID().toString());
		return role;
	}
	
	/**
	 * Gets the stock object.
	 *
	 * @return the stock object
	 */
	public static StockDto getStockObject() {
		StockDto stock = new StockDto();
		stock.setPrice(200.50);
		stock.setStockId(UUID.randomUUID().toString());
		stock.setDate(new Date());
		stock.setTimeStamp(new Date().getTime());
		return stock;
	}

	/**
	 * Gets the stock dao object.
	 *
	 * @return the stock dao object
	 */
	public static StockDao getStockDaoObject() {
		StockDao stock = new StockDao();
		stock.setPrice(200.50);
		stock.setStockId(UUID.randomUUID().toString());
		stock.setDate(new Date());
		stock.setTimeStamp(new Date().getTime());
		return stock;
	}

	/**
	 * Gets the company object.
	 *
	 * @return the company object
	 */
	public static CompanyDto getCompanyObject() {
		CompanyDto company = new CompanyDto();
		company.setCompanyCode("abc");
		company.setCompanyId(UUID.randomUUID().toString());
		company.setCompanyName("ABC Company");
		company.setCompanyTurnover("10000000000");
		company.setCompanyWebsite("http://www.google.com");
		company.setCompanyCeo("Riya Mittal");
		company.setStockExchange("NSE");

		List<StockDto> stocks = new ArrayList<StockDto>();
		stocks.add(getStockObject());
		company.setStocks(stocks);
		return company;
	}

}
