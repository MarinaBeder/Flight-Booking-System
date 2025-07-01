package com.FlighSystem.controller;

import com.FlighSystem.controller.dto.request.FlightRequest;
import com.FlighSystem.controller.dto.response.ApiResponse;
import com.FlighSystem.domain.Flight;
import com.FlighSystem.service.FlightService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

	private final FlightService flightService;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Page<Flight> getAllFlights(
	    @RequestParam(defaultValue = "0") int page,
	    @RequestParam(defaultValue = "10") int size
	) {
	    Pageable pageable = PageRequest.of(page, size);
	    return flightService.getAllFlights(pageable);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Flight getFlight(@PathVariable Long id) {
		return flightService.getFlight(id);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Flight createFlight(@RequestBody FlightRequest request) {
		return flightService.createFlight(request);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Flight updateFlight(@PathVariable Long id, @RequestBody FlightRequest request) {
		return flightService.updateFlight(id, request);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteFlight(@PathVariable Long id) {
		flightService.cancelFlight(id);
        return ResponseEntity.ok(new ApiResponse("Flight is Cancelled"));

	}

	@GetMapping("/search")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public Page<Flight> searchFlights(@RequestParam(required = false) String from,
			@RequestParam(required = false) String to, @RequestParam(required = false) BigDecimal fareMin,
			@RequestParam(required = false) BigDecimal fareMax,
			@RequestParam(defaultValue = "0") int page,
		    @RequestParam(defaultValue = "10") int size) {
	   
		 Pageable pageable = PageRequest.of(page, size);
		return flightService.searchFlights(from, to, fareMin, fareMax, pageable);
	}
}
