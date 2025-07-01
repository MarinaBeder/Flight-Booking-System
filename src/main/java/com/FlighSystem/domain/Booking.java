package com.FlighSystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@Table(name = "bookings")
@EntityListeners(AuditingEntityListener.class) // Enables auditing
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketClass ticketClass;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    private boolean canceled = false;

	@CreatedDate
	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Column(insertable  = false)
    @Temporal(TemporalType.TIMESTAMP)

	private LocalDateTime lastModified ;
    
    public enum TicketClass {
        ECONOMY,
        PREMIUM_ECONOMY,
        BUSINESS,
        FIRST_CLASS
    }
}

