package com.FlighSystem.controller.dto.request;

import java.math.BigDecimal;

import com.FlighSystem.domain.Booking.TicketClass;

import lombok.Data;

@Data
public class UpgradeRequest {
    private TicketClass NewTicketClass;
    private BigDecimal newPrice;
}
