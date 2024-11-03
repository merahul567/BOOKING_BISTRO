package com.bookingbistro.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookingbistro.userservice.model.Role;
import com.bookingbistro.userservice.model.User;
import com.bookingbistro.userservice.repository.RoleRepository;
import com.bookingbistro.userservice.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User registerUser(User newUser) {
		String encodedPassword = passwordEncoder.encode(newUser.getPassword());
		
		//String encodedPassword = newUser.getPassword();
		
		Role defaultRole = roleRepository.findByRoleName("CUSTOMER");
		
		newUser.setPassword(encodedPassword);
		
		if(defaultRole == null) {
			defaultRole = new Role("CUSTOMER");
			roleRepository.save(defaultRole);
		}
		newUser.getRoles().add(defaultRole);
		
		return userRepository.save(newUser);
	}
	
	
	public User findUserByEmailId(String emaildId) {
		return userRepository.findByEmailId(emaildId);
	}
}
