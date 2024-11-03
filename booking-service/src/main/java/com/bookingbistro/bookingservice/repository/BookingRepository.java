package com.bookingbistro.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookingbistro.bookingservice.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{

}
