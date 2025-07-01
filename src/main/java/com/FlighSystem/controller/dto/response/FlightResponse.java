package com.FlighSystem.controller.dto.response;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FlightResponse {
  private Long id;
  private String departureLocation;
  private String arrivalLocation;
  private BigDecimal fare;
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;
}

