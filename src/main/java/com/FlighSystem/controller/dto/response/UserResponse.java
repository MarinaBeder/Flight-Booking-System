package com.FlighSystem.controller.dto.response;

import java.util.Date;
import java.util.UUID;

import com.FlighSystem.domain.Address;
import com.FlighSystem.domain.Gender;
import com.FlighSystem.domain.Role;

import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

	private String fullname;
	private String email;

	@Embedded
	private Address address;
	private Integer age;
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	private Role role;

}
