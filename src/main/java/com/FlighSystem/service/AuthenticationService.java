package com.FlighSystem.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.FlighSystem.config.JwtService;
import com.FlighSystem.controller.dto.request.AuthenticationRequest;
import com.FlighSystem.controller.dto.response.AuthenticationResponse;
import com.FlighSystem.domain.User;
import com.FlighSystem.exceptions.UnauthorizedException;
import com.FlighSystem.repository.UserRepository;
import com.FlighSystem.token.Token;
import com.FlighSystem.token.TokenRepository;
import com.FlighSystem.token.TokenType;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {

	private final UserRepository repository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final TokenRepository tokenRepo;
	private final UserRepository userRepo;

	public AuthenticationService(UserRepository repository, JwtService jwtService,
			AuthenticationManager authenticationManager, TokenRepository tokenRepo, UserRepository userRepo) {

		this.repository = repository;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.tokenRepo = tokenRepo;
		this.userRepo = userRepo;
	}

	private void saveUserToken(User user, String jwtToken) {
		Token token = new Token();
		token.setToken(jwtToken);
		token.setExpired(false);
		token.setRevoked(false);
		token.setUser(user);
		token.setTokenType(TokenType.BEARER);
		tokenRepo.save(token);
	}

	private void revokeAllUserTokens(User user) {
		var validUserTokens = tokenRepo.findAllValidTokensByUser(user.getId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(t -> {
			t.setExpired(true);
			t.setRevoked(true);
		});
		tokenRepo.saveAll(validUserTokens);
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		System.out.print(request);
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = repository.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, refreshToken);

		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		authenticationResponse.setAccessToken(jwtToken);
		authenticationResponse.setRefreshToken(refreshToken);
		return authenticationResponse;
	}

	public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response)
			throws StreamWriteException, DatabindException, IOException {
		final String authHeader = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userEmail;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			//return;
			throw new UnauthorizedException("Missing refresh token");

		}
		refreshToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(refreshToken);
		if (userEmail != null) {
			var user = this.repository.findByEmail(userEmail).orElseThrow();

			var isTokenValid = isRefreshTokenNotRevoked(refreshToken);

			if (jwtService.isTokenValid(refreshToken, user) && isTokenValid) {
				var accessToken = jwtService.generateToken(user);
				AuthenticationResponse authResponse = new AuthenticationResponse();

				authResponse.setAccessToken(accessToken);
				authResponse.setRefreshToken(refreshToken);

				//new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
				return authResponse;
			} else {
				throw new UnauthorizedException("invalid Refresh Token");

			}
		}
		throw new UnauthorizedException("Invalid Refresh Token");
	}

	public Boolean isRefreshTokenNotRevoked(String refreshToken) {
		var isTokenValid = tokenRepo.findByToken(refreshToken).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);
		return isTokenValid;

	}
}