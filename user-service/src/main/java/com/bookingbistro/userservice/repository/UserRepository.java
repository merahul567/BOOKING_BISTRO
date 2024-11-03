package com.bookingbistro.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookingbistro.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmailId(String emaildId);

}
