package com.stock.microservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.microservice.dto.RoleDto;
import com.stock.microservice.dto.UserDto;
import com.stock.microservice.entity.UserDao;
import com.stock.microservice.service.UserService;
import com.stock.microservice.util.MockSample;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserService userService;

	@MockBean
	UserDetailsService userDetailsService;

	@MockBean
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	void saveUserTest() throws Exception {

		when(userService.addUser(ArgumentMatchers.any(UserDto.class))).thenReturn(MockSample.getUserDao());
		String requestBody = new ObjectMapper().writeValueAsString(MockSample.getUserDto());
		this.mockMvc.perform(post("/user/add").with(user("admin").roles("ADMIN")).content(requestBody)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn();
	}

	@Test
	void saveRoleTest() throws Exception {

		when(userService.addRole(ArgumentMatchers.any(RoleDto.class))).thenReturn(MockSample.getRoleDao());
		String requestBody = new ObjectMapper().writeValueAsString(MockSample.getRoleDto());
		this.mockMvc.perform(post("/user/role/add").with(user("admin").roles("ADMIN")).content(requestBody)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn();
	}

	@Test
	void getUserTest() throws Exception {
		when(userService.getUser("riya")).thenReturn(MockSample.getUserDao());
		this.mockMvc.perform(get("/user/riya").with(user("admin").roles("ADMIN"))).andDo(print())
				.andExpect(status().isOk()).andReturn();
	}
	
	@Test
	void getAllUserTest() throws Exception {
		List<UserDao> users = new ArrayList<>();
		users.add(MockSample.getUserDao());
		when(userService.getAllUsers()).thenReturn(users);
		this.mockMvc.perform(get("/user/all").with(user("admin").roles("ADMIN"))).andDo(print())
				.andExpect(status().isOk()).andReturn();
	}
	
	@Test
	void addRoleToUserTest() throws Exception {
		this.mockMvc.perform(put("/user/riya/role/ROLE_ADMIN").with(user("admin").roles("ADMIN"))).andDo(print())
				.andExpect(status().isOk()).andReturn();
	}

}
