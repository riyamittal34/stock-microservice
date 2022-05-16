package com.stock.microservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stock.microservice.dto.RoleDto;
import com.stock.microservice.dto.UserDto;
import com.stock.microservice.entity.RoleDao;
import com.stock.microservice.entity.UserDao;
import com.stock.microservice.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping(value = "/add")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<UserDao> saveUser(@RequestBody UserDto user) {
		return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
	}

	@PostMapping(value = "/role/add")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<RoleDao> saveRole(@RequestBody RoleDto role) {
		return new ResponseEntity<>(userService.addRole(role), HttpStatus.OK);
	}

	@GetMapping(value = "/{username}")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<UserDao> getUser(@PathVariable("username") String username) {
		return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<List<UserDao>> getAllUser() {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@PutMapping(value = "/{username}/role/{rolename}")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<String> AddRoleToUser(@PathVariable("username") String username,
			@PathVariable("rolename") String roleName) {
		userService.addRoleToUser(username, roleName);
		return new ResponseEntity<>("Role " + roleName + " added to user " + username, HttpStatus.OK);
	}
}
