package com.bookingbistro.userservice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookingbistro.userservice.model.Role;
import com.bookingbistro.userservice.model.User;
import com.bookingbistro.userservice.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailId(username);
 
        if(user == null) {
        	throw new UsernameNotFoundException("user not found");
        }
        
        return new org.springframework.security.core.userdetails.User(
        		user.getEmailId(), user.getPassword(), getAuthorities(user));
    }

	private Collection<? extends GrantedAuthority> getAuthorities(User user) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for(Role role: user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
		}
		
		return authorities;
	}
}

