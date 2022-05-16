package com.stock.microservice.service;

import java.util.List;

import com.stock.microservice.dto.RoleDto;
import com.stock.microservice.dto.UserDto;
import com.stock.microservice.entity.RoleDao;
import com.stock.microservice.entity.UserDao;

public interface UserService {

	public UserDao addUser(UserDto user);
	
	public RoleDao addRole(RoleDto role);
	
	public void addRoleToUser(String username, String roleName);
	
	public UserDao getUser(String username);
	
	public List<UserDao> getAllUsers();
}
