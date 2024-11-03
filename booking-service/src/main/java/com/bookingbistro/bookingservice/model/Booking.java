package com.bookingbistro.bookingservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String CustomerName;
	private Long numberOfGuests;
	private LocalDateTime bookingDate;
	private String venue;
	private String timeSlot;
	private String workingHours;
	private String status;
	
	//Since Lombok generates constructor with all field but we do not need id 
	public Booking(Long numberOfGuests, LocalDateTime bookingDate, String venue, String timeSlot, String workingHours,
			String status) {
		super();
		this.numberOfGuests = numberOfGuests;
		this.bookingDate = bookingDate;
		this.venue = venue;
		this.timeSlot = timeSlot;
		this.workingHours = workingHours;
		this.status = status;
	}
	
	
	
}
