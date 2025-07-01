package com.FlighSystem.service;


import com.FlighSystem.controller.dto.request.FlightRequest;
import com.FlighSystem.domain.Flight;
import com.FlighSystem.domain.Flight.FlightStatus;
import com.FlighSystem.exceptions.AlreadyExistException;
import com.FlighSystem.exceptions.NotFoundException;
import com.FlighSystem.repository.FlightRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
@RequiredArgsConstructor
public class FlightService {

  private final FlightRepository flightRepository;

  public Page<Flight> getAllFlights(Pageable pageable) {
	    return flightRepository.findAll(pageable);
	}

  public Flight getFlight(Long id) {
    return flightRepository.findById(id)
  		  .orElseThrow(() -> new NotFoundException("Fligh not found"));
  }

  public Flight createFlight(FlightRequest request) {
    Flight flight = new Flight();
    flight.setDepartureLocation(request.getDepartureLocation());
    flight.setArrivalLocation(request.getArrivalLocation());
    flight.setFare(request.getFare());
    flight.setDepartureTime(request.getDepartureTime());
    flight.setArrivalTime(request.getArrivalTime());
    return flightRepository.save(flight);
  }

  public Flight updateFlight(Long id, FlightRequest request) {
    Flight flight = getFlight(id);
    flight.setDepartureLocation(request.getDepartureLocation());
    flight.setArrivalLocation(request.getArrivalLocation());
    flight.setFare(request.getFare());
    flight.setDepartureTime(request.getDepartureTime());
    flight.setArrivalTime(request.getArrivalTime());
    return flightRepository.save(flight);
  }
/*
  public void deleteFlight(Long id) {
    flightRepository.deleteById(id);
  }
*/
  @Transactional
  public void cancelFlight(Long id) {
      Flight flight = flightRepository.findById(id)
    		  .orElseThrow(() -> new NotFoundException("Flight not found"));

      if (flight.getFlightStatus() == FlightStatus.CANCELED) {
	        throw new AlreadyExistException("Flight is already canceled.");

      }

      flight.setFlightStatus(FlightStatus.CANCELED);
      flightRepository.save(flight);
  }

  public Page<Flight> searchFlights(String from, String to, BigDecimal fareMin, BigDecimal fareMax, Pageable pageable) {
	    // Convert empty/blank strings to null
	    from = (from == null || from.trim().isEmpty()) ? null : from.trim();
	    to = (to == null || to.trim().isEmpty()) ? null : to.trim();
	    
	  return flightRepository.searchFlights(from, to, fareMin, fareMax, pageable);
  }
}
