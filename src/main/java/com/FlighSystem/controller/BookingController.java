package com.FlighSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.FlighSystem.controller.dto.request.BookingRequest;
import com.FlighSystem.controller.dto.request.UpgradeRequest;
import com.FlighSystem.controller.dto.response.ApiResponse;
import com.FlighSystem.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> bookFlight(@RequestBody BookingRequest request) {
        bookingService.bookFlight(request);
        return ResponseEntity.ok(new ApiResponse("Flight booked successfully"));
    }

    @PutMapping("/upgrade/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> upgradeBooking(@PathVariable Long id, @RequestBody UpgradeRequest request) {
        bookingService.upgradeBooking(id, request);
        return ResponseEntity.ok(new ApiResponse("Booking upgraded successfully"));
    }

    @DeleteMapping("/cancel/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok(new ApiResponse("Booking canceled successfully"));
    }
}


