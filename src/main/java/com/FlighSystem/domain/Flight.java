package com.FlighSystem.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@Table(name = "flights")
@EntityListeners(AuditingEntityListener.class) // Enables auditing
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String departureLocation;

    @Column(nullable = false)
    private String arrivalLocation;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fare;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;
    
    @Column(nullable = false)
    private FlightStatus flightStatus = FlightStatus.SCHEDULED;
    
    
	@CreatedDate
	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Column(insertable  = false)
    @Temporal(TemporalType.TIMESTAMP)

	private LocalDateTime lastModified ;
	
    public enum FlightStatus {
        SCHEDULED,
        CANCELED
    }
}



