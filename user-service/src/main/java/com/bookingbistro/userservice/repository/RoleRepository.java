package com.bookingbistro.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookingbistro.userservice.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByRoleName(String roleName);

}
