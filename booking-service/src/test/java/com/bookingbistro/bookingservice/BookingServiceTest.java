package com.bookingbistro.bookingservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.bookingbistro.bookingservice.dto.Restaurant;
import com.bookingbistro.bookingservice.model.Booking;
import com.bookingbistro.bookingservice.repository.BookingRepository;
import com.bookingbistro.bookingservice.service.BookingService;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BookingService bookingService;

    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        restaurant = new Restaurant();
        restaurant.setRestaurantName("Test Restaurant");
    }

    @Test
    public void testSaveBookingSuccess() {
        Booking booking = new Booking();
        booking.setVenue("Test Restaurant");
        booking.setCustomerName("John Doe");
        booking.setNumberOfGuests(4L);
        booking.setBookingDate(LocalDateTime.now());
        booking.setTimeSlot("18:00");
        booking.setWorkingHours("9 AM - 9 PM");
        booking.setStatus("pending");

        when(restTemplate.getForObject(any(String.class), eq(Restaurant.class))).thenReturn(restaurant);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking savedBooking = bookingService.saveBooking(booking);

        assertNotNull(savedBooking);
        assertEquals(booking.getCustomerName(), savedBooking.getCustomerName());
        verify(bookingRepository).save(booking);
    }

    @Test
    public void testGetAllBookings() {
        Booking booking1 = new Booking(1L, "John Doe", 4L, LocalDateTime.now(), "Test Restaurant", "18:00", "9 AM - 9 PM", "pending");
        Booking booking2 = new Booking(2L, "Jane Doe", 2L, LocalDateTime.now(), "Test Restaurant", "19:00", "9 AM - 9 PM", "pending");
        
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        List<Booking> bookings = bookingService.getAllBookings();

        assertEquals(2, bookings.size());
        verify(bookingRepository).findAll();
    }

    @Test
    public void testGetBookingById() {
        Booking booking = new Booking(1L, "John Doe", 4L, LocalDateTime.now(), "Test Restaurant", "18:00", "9 AM - 9 PM", "pending");
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        Booking foundBooking = bookingService.getBookingById(1L);

        assertNotNull(foundBooking);
        assertEquals(booking.getCustomerName(), foundBooking.getCustomerName());
        verify(bookingRepository).findById(1L);
    }

    @Test
    public void testUpdateBookingSuccess() {
        Booking existingBooking = new Booking(1L, "John Doe", 4L, LocalDateTime.now(), "Test Restaurant", "18:00", "9 AM - 9 PM", "pending");
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(existingBooking));

        Booking updatedBookingDetails = new Booking();
        updatedBookingDetails.setCustomerName("Jane Doe");
        updatedBookingDetails.setNumberOfGuests(2L);
        
        Booking updatedBooking = bookingService.updateBooking(1L, updatedBookingDetails);

        assertNotNull(updatedBooking);
        assertEquals("Jane Doe", existingBooking.getCustomerName());
        assertEquals(2, existingBooking.getNumberOfGuests());
        verify(bookingRepository).save(existingBooking);
    }

    @Test
    public void testUpdateBookingStatusSuccess() throws Exception {
        Booking booking = new Booking(1L, "John Doe", 4L, LocalDateTime.now(), "Test Restaurant", "18:00", "9 AM - 9 PM", "pending");
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        String result = bookingService.updateBookingStatus(1L, "confirmed");

        assertEquals("Booking status updated to: confirmed", result);
        assertEquals("confirmed", booking.getStatus());
        verify(bookingRepository).save(booking);
    }

    @Test
    public void testDeleteBooking() {
        doNothing().when(bookingRepository).deleteById(anyLong());

        bookingService.deleteBooking(1L);

        verify(bookingRepository).deleteById(1L);
    }
}

