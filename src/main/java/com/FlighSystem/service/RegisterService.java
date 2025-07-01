package com.FlighSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.FlighSystem.config.JwtService;
import com.FlighSystem.controller.dto.request.RegisterRequest;
import com.FlighSystem.controller.dto.response.UserResponse;
import com.FlighSystem.domain.Role;
import com.FlighSystem.domain.User;
import com.FlighSystem.exceptions.AlreadyExistException;
import com.FlighSystem.exceptions.NonUniqueEmailException;
import com.FlighSystem.repository.UserRepository;
import com.FlighSystem.token.TokenRepository;

import jakarta.transaction.Transactional;

@Service
public class RegisterService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepo;

	public RegisterService(PasswordEncoder passwordEncoder, UserRepository userRepo) {
		this.passwordEncoder = passwordEncoder;
		this.userRepo = userRepo;
	}

	public UserResponse createUser(RegisterRequest request) {

	    if (userRepo.findByEmail(request.getEmail()).isPresent()) {
	        throw new AlreadyExistException("Email already exists: " + request.getEmail());
	    }
	    
		User user = new User(request.getFullName(), request.getEmail(), passwordEncoder.encode(request.getPassword()),
				request.getUsername(), request.getRole(), request.getAddress(), request.getAge(), request.getGender()

		);
		
		userRepo.save(user);
		return new UserResponse(user.getFullName(), user.getEmail(), user.getAddress(), user.getAge(),
				user.getGender(), user.getRole());

	}
}
