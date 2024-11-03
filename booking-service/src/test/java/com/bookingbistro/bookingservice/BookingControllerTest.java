package com.bookingbistro.bookingservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bookingbistro.bookingservice.controller.BookingController;
import com.bookingbistro.bookingservice.model.Booking;
import com.bookingbistro.bookingservice.service.BookingService;

public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    public void testCreateBooking() throws Exception {
        Booking booking = new Booking(1L, "John Doe", 4L, LocalDateTime.now(), "Venue A", "18:00", "9:00-22:00", "pending");

        when(bookingService.saveBooking(any(Booking.class))).thenReturn(booking);

        mockMvc.perform(post("/api/bookings/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerName\":\"John Doe\",\"numberOfGuests\":4,\"bookingDate\":\"2024-10-26T18:00:00\",\"venue\":\"Venue A\",\"timeSlot\":\"18:00\",\"workingHours\":\"9:00-22:00\",\"status\":\"pending\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John Doe"))
                .andExpect(jsonPath("$.numberOfGuests").value(4));
    }

    @Test
    public void testGetAllBookings() throws Exception {
        Booking booking = new Booking(1L, "John Doe", 4L, LocalDateTime.now(), "Venue A", "18:00", "9:00-22:00", "pending");
        when(bookingService.getAllBookings()).thenReturn(Collections.singletonList(booking));

        mockMvc.perform(get("/api/bookings/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerName").value("John Doe"));
    }

    @Test
    public void testGetBookingById() throws Exception {
        Booking booking = new Booking(1L, "John Doe", 4L, LocalDateTime.now(), "Venue A", "18:00", "9:00-22:00", "pending");
        when(bookingService.getBookingById(1L)).thenReturn(booking);

        mockMvc.perform(get("/api/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    public void testUpdateBooking() throws Exception {
        Booking booking = new Booking(1L, "John Doe", 4L, LocalDateTime.now(), "Venue A", "18:00", "9:00-22:00", "pending");
        Booking updatedBooking = new Booking(1L, "Jane Doe", 5L, LocalDateTime.now(), "Venue A", "19:00", "9:00-22:00", "confirmed");

        when(bookingService.updateBooking(eq(1L), any(Booking.class))).thenReturn(updatedBooking);

        mockMvc.perform(put("/api/bookings/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerName\":\"Jane Doe\",\"numberOfGuests\":5,\"bookingDate\":\"2024-10-26T19:00:00\",\"venue\":\"Venue A\",\"timeSlot\":\"19:00\",\"workingHours\":\"9:00-22:00\",\"status\":\"confirmed\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Jane Doe"));
    }

    @Test
    public void testDeleteBooking() throws Exception {
        mockMvc.perform(delete("/api/bookings/1"))
                .andExpect(status().isOk());
    }
}
