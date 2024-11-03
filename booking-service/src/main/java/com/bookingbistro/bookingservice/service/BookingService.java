package com.bookingbistro.bookingservice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bookingbistro.bookingservice.dto.Restaurant;
import com.bookingbistro.bookingservice.model.Booking;
import com.bookingbistro.bookingservice.repository.BookingRepository;

@Service
public class BookingService {
	
	private String RESTAURANT_SERVICE_URL = "http://localhost:8002/api/restaurants/restaurantName/";
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Booking saveBooking(Booking booking) {
		
		Restaurant restaurant = callRestaurantService(booking.getVenue());
		
		if(booking.getVenue().equals(restaurant.getRestaurantName())) {
			return bookingRepository.save(booking);
		}
		
		return null;
	}
	
	public List<Booking> getAllBookings(){
		return bookingRepository.findAll();
	}
	
	public Booking getBookingById(Long id) {
		return bookingRepository.findById(id).orElse(null);
	}
	
	public Booking updateBooking(Long id, Booking bookingDetails) {
		Booking booking = bookingRepository.findById(id).orElse(null);
		
		if(booking != null) {
			if(bookingDetails.getVenue() != null) {
				Restaurant restaurant = callRestaurantService(bookingDetails.getVenue());
				if(bookingDetails.getVenue().equals(restaurant.getRestaurantName())) {
					booking.setVenue(bookingDetails.getVenue());
				}
			}
			booking.setCustomerName(bookingDetails.getCustomerName());
			booking.setNumberOfGuests(bookingDetails.getNumberOfGuests());
			booking.setBookingDate(bookingDetails.getBookingDate());
			booking.setTimeSlot(bookingDetails.getTimeSlot());
			bookingRepository.save(booking);
		}
		
		return booking;
	}
	
	//@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public String updateBookingStatus(Long id, String newStatus) throws Exception{
		Booking booking = bookingRepository.findById(id).orElseThrow(() -> new Exception("Booking not found"));
		
		if(!Arrays.asList("confirmed", "rejected", "cancelled").contains(newStatus)) {
			throw new Exception("Invalid status value");
		}
		
		if(booking.getStatus().equals("pending")) {
			booking.setStatus(newStatus);
			bookingRepository.save(booking);
			return "Booking status updated to: "+ newStatus;
		}else {
			throw new Exception("Booking status cannot be updated from current state");
		}
	}
	
	public void deleteBooking(Long id) {
		 bookingRepository.deleteById(id);
	}
	
	private Restaurant callRestaurantService(String restaurantName) {
		String url = RESTAURANT_SERVICE_URL + restaurantName;
		Restaurant response = restTemplate.getForObject(url, Restaurant.class);
				
		return response;
	}
	
}
