package com.FlighSystem.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.FlighSystem.controller.dto.request.BookingRequest;
import com.FlighSystem.controller.dto.request.UpgradeRequest;
import com.FlighSystem.domain.Booking;
import com.FlighSystem.domain.Flight;
import com.FlighSystem.domain.Flight.FlightStatus;
import com.FlighSystem.exceptions.AlreadyExistException;
import com.FlighSystem.exceptions.NotFoundException;
import com.FlighSystem.repository.BookingRepository;
import com.FlighSystem.repository.FlightRepository;
import com.FlighSystem.repository.UserRepository;

@Service
@Transactional
public class BookingService {

	private final BookingRepository bookingRepository;
	private final UserRepository userRepository; 
	private final FlightRepository flightRepository;

	public BookingService(BookingRepository bookingRepository, UserRepository userRepository,
			FlightRepository flightRepository) {
		this.bookingRepository = bookingRepository;
		this.userRepository = userRepository;
		this.flightRepository = flightRepository;
	}

	public void bookFlight(BookingRequest request) {
		Booking booking = new Booking();
		booking.setUser(
				userRepository.findById(request.getUserId()).orElseThrow(() -> new NotFoundException("User not found")));

		booking.setFlight(flightRepository.findById(request.getFlightId())
	    		  .orElseThrow(() -> new NotFoundException("Flight not found")));

		booking.setTicketClass(request.getTicketClass());
		booking.setPrice(request.getPrice());

		bookingRepository.save(booking);
	}


	@Transactional
	public void upgradeBooking(Long bookingId, UpgradeRequest request) {
	    Booking booking = bookingRepository.findById(bookingId)
	        .orElseThrow(() -> new NotFoundException("Booking not found"));

	    Flight flight = booking.getFlight();
	    if (flight.getFlightStatus().equals(FlightStatus.CANCELED)) {

	        throw new AlreadyExistException("Cannot upgrade: Flight is " + flight.getFlightStatus());
	    }

	    // Business logic: e.g., validate new class, price difference, etc.
	    booking.setTicketClass(request.getNewTicketClass());
	    booking.setPrice(request.getNewPrice());
	    bookingRepository.save(booking);
	}

	
	public void cancelBooking(Long bookingId) {
		Booking booking = bookingRepository.findById(bookingId)
	    .orElseThrow(() -> new NotFoundException("Booking not found"));

	    if (booking.isCanceled()) {
	        throw new AlreadyExistException("Your Booking already cancelled before");
	    }
		booking.setCanceled(true);

		bookingRepository.save(booking);
	}
}
