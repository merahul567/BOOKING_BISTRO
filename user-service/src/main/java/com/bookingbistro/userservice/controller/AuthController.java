package com.bookingbistro.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingbistro.userservice.dto.AuthenticationRequest;
import com.bookingbistro.userservice.dto.AuthenticationResponse;
import com.bookingbistro.userservice.model.User;
import com.bookingbistro.userservice.service.CustomUserDetailsService;
import com.bookingbistro.userservice.service.UserService;
import com.bookingbistro.userservice.util.JwtUtil;

@RestController
@RequestMapping("/api/users")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmailId(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmailId());
        
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User user){
		User foundUser = userService.findUserByEmailId(user.getEmailId());
		System.out.println(foundUser.getFullName());
		
		if(foundUser != null && passwordEncoder.matches( user.getPassword(), foundUser.getPassword())) {
			return ResponseEntity.ok("Login Successful");
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
	}
}	
