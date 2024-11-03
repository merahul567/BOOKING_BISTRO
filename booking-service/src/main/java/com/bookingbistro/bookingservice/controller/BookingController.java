package com.bookingbistro.bookingservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingbistro.bookingservice.dto.BookingStatusUpdateRequest;
import com.bookingbistro.bookingservice.model.Booking;
import com.bookingbistro.bookingservice.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@PostMapping("/")
	public ResponseEntity<Booking> createBooking(@RequestBody Booking newBooking) {
		return ResponseEntity.ok(bookingService.saveBooking(newBooking));
	}
	
	@GetMapping("/")
	public List<Booking> getAllBookings() {
	    return bookingService.getAllBookings();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
	    Booking booking = bookingService.getBookingById(id);
	    
	    return booking!= null ? ResponseEntity.ok(booking) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking bookingDetails) {
	    Booking updatedBooking = bookingService.updateBooking(id, bookingDetails);
	    
	    return updatedBooking!= null ? ResponseEntity.ok(updatedBooking) : ResponseEntity.notFound().build();
	}
	

	@PatchMapping("/{id}/status")
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	public ResponseEntity<String> updateBookingStatus(@PathVariable Long id, @RequestBody BookingStatusUpdateRequest statusDetails) {
	    try {
	    	String result = bookingService.updateBookingStatus(id, statusDetails.getStatus());
	    	return ResponseEntity.ok(result);
	    }catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
	    bookingService.deleteBooking(id);
	    
	    return ResponseEntity.ok().build();
	}
}
