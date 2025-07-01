package com.FlighSystem.controller.dto.request;

import java.math.BigDecimal;

import com.FlighSystem.domain.Booking.TicketClass;

import lombok.Data;

@Data
public class BookingRequest {

    private Long userId;
    private Long flightId;
    private TicketClass ticketClass;
    private BigDecimal price;
    
}
