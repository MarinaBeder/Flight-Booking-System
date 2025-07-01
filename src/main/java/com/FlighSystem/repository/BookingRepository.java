package com.FlighSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FlighSystem.domain.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
