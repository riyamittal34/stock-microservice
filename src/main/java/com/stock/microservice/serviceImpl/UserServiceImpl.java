package com.stock.microservice.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stock.microservice.dto.RoleDto;
import com.stock.microservice.dto.UserDto;
import com.stock.microservice.entity.RoleDao;
import com.stock.microservice.entity.UserDao;
import com.stock.microservice.repository.RoleRepository;
import com.stock.microservice.repository.UserRepository;
import com.stock.microservice.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	private final Logger applicationLog = LoggerFactory.getLogger("[APPLICATION]");
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public UserDao addUser(UserDto user) {
		UserDao userDao = new UserDao();
		userDao.setName(user.getName());
		userDao.setUsername(user.getUsername());
		userDao.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(userDao);
	}

	@Override
	public RoleDao addRole(RoleDto role) {
		RoleDao roleDao = new RoleDao();
		roleDao.setRoleName(role.getRoleName());
		return roleRepository.save(roleDao);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		UserDao user = userRepository.findByUsername(username);
		RoleDao role = roleRepository.findByRoleName(roleName);
		applicationLog.info("ROLE: {}", role.getRoleName());
		List<RoleDao> roles = user.getRoles();
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
	}

	@Override
	public UserDao getUser(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<UserDao> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDao user = userRepository.findByUsername(username);
		if (user == null) {
			applicationLog.error("User not found in database");
			throw new UsernameNotFoundException("User not found in database");
		} else {
			applicationLog.info("User found in database");
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		});
		return new User(user.getUsername(), user.getPassword(), authorities);
	}
}
