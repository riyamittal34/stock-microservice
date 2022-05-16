package com.stock.microservice.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.stock.microservice.entity.RoleDao;
import com.stock.microservice.entity.UserDao;
import com.stock.microservice.repository.RoleRepository;
import com.stock.microservice.repository.UserRepository;
import com.stock.microservice.serviceImpl.UserServiceImpl;
import com.stock.microservice.util.MockSample;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {

	@InjectMocks
	UserServiceImpl userService;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	RoleRepository roleRepository;
		
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void addUserTest() {
		Assertions.assertThrows(Exception.class, () -> {
			when(userRepository.save(ArgumentMatchers.any(UserDao.class))).thenReturn(MockSample.getUserDao());
			userService.addUser(MockSample.getUserDto());
		});
	}
	
	@Test
	void addRoleTest() {
		when(roleRepository.save(ArgumentMatchers.any(RoleDao.class))).thenReturn(MockSample.getRoleDao());
		userService.addRole(MockSample.getRoleDto());
	}

	@Test
	void getUserTest() {
		when(userRepository.findByUsername("riya")).thenReturn(MockSample.getUserDao());
		userService.getUser("riya");
	}
	
	@Test
	void getAllUsersTest() {
		List<UserDao> users = new ArrayList<UserDao>();
		users.add(MockSample.getUserDao());
		when(userRepository.findAll()).thenReturn(users);
		userService.getAllUsers();
	}
	
	@Test
	void addRoleToUserTest() {
		
		when(userRepository.findByUsername("riya")).thenReturn(MockSample.getUserDao());
		when(roleRepository.findByRoleName("ROLE_ADMIN")).thenReturn(MockSample.getRoleDao());
		userService.addRoleToUser("riya", "ROLE_ADMIN");
	}
	
	@Test
	void loadUserByUsernameTest() {
		when(userRepository.findByUsername("riya")).thenReturn(MockSample.getUserDao());
		userService.loadUserByUsername("riya");
	}
	
	@Test
	void loadUserByUsernameNullTest() {
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			when(userRepository.findByUsername("riya")).thenReturn(null);
			userService.loadUserByUsername("riya");
		});
	}
}
