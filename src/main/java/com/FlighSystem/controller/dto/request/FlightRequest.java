package com.FlighSystem.controller.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FlightRequest {

    @NotNull(message = "Departure location is required.")
    private String departureLocation;

    @NotNull(message = "Arrival location is required.")
    private String arrivalLocation;

    @NotNull(message = "Fare must be provided.")
    private BigDecimal fare;

    @NotNull(message = "Departure time must be specified.")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time must be specified.")
    private LocalDateTime arrivalTime;
}

