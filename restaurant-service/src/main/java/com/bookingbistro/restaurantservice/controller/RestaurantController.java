package com.bookingbistro.restaurantservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingbistro.restaurantservice.model.Restaurant;
import com.bookingbistro.restaurantservice.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
	
	@Autowired
	private RestaurantService restaurantService;
	
	@PostMapping("/register")
	public Restaurant registerUser(@RequestBody Restaurant newRestaurant) {
		return restaurantService.registerRestaurant(newRestaurant);
	}
	
	@GetMapping("/all")
	public List<Restaurant> getAllRestaurant() {
	    return restaurantService.findAllRestaurant();
	}
	
	@GetMapping("/restaurantName/{restaurantName}")
	public Restaurant getRestaurantByName(@PathVariable String restaurantName) {
	    return restaurantService.findRestaurantByName(restaurantName);
	}
}
