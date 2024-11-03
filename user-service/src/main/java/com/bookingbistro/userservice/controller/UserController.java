package com.bookingbistro.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingbistro.userservice.model.User;
import com.bookingbistro.userservice.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public User registerUser(@RequestBody User newUser) {
		return userService.registerUser(newUser);
	}
	
	@GetMapping("/emailId/{emailId}")
	public User getUserByEmailId(@PathVariable String emailId) {
	    return userService.findUserByEmailId(emailId);
	}
}	
