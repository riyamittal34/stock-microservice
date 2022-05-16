package com.stock.microservice.dto;

import java.util.List;

import com.stock.microservice.entity.RoleDao;

public class UserDto {

	private String userId;
	private String name;
	private String username;
	private String password;
	private List<RoleDao> roles;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RoleDao> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDao> roles) {
		this.roles = roles;
	}
}
