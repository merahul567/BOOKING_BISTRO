package com.bookingbistro.restaurantservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookingbistro.restaurantservice.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
	
	Restaurant findRestaurantByRestaurantName(String restaurantName);

}
