package com.FlighSystem.repository;


import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.FlighSystem.domain.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

	@Query("""
			  SELECT f FROM Flight f
			  WHERE (:from IS NULL OR LOWER(f.departureLocation) = LOWER(:from))
			    AND (:to IS NULL OR LOWER(f.arrivalLocation) = LOWER(:to))
			    AND (:fareMin IS NULL OR f.fare >= :fareMin)
			    AND (:fareMax IS NULL OR f.fare <= :fareMax)
			""")
			Page<Flight> searchFlights(
			  @Param("from") String from,
			  @Param("to") String to,
			  @Param("fareMin") BigDecimal fareMin,
			  @Param("fareMax") BigDecimal fareMax,
			  Pageable pageable
			);

}