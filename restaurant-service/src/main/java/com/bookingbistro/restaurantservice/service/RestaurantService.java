package com.bookingbistro.restaurantservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingbistro.restaurantservice.model.Restaurant;
import com.bookingbistro.restaurantservice.repository.RestaurantRepository;

@Service
public class RestaurantService {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	public Restaurant registerRestaurant(Restaurant newresRestaurant) {
		
		return restaurantRepository.save(newresRestaurant);
	}
	
	public List<Restaurant> findAllRestaurant(){
		return restaurantRepository.findAll();
	}
	
	public Restaurant findRestaurantByName(String restaurantName) {
		return restaurantRepository.findRestaurantByRestaurantName(restaurantName);
	}
}
