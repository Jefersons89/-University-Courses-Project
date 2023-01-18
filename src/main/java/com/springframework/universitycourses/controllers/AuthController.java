package com.springframework.universitycourses.controllers;

import com.springframework.universitycourses.api.v1.model.AuthResponseDTO;
import com.springframework.universitycourses.api.v1.model.LoginDTO;
import com.springframework.universitycourses.configuration.security.JWTTokenGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(AuthController.BASE_URL)
public class AuthController
{
	public static final String BASE_URL = "/api/auth";

	private final AuthenticationManager authenticationManager;
	private final JWTTokenGenerator tokenGenerator;

	public AuthController(final AuthenticationManager authenticationManager, final JWTTokenGenerator tokenGenerator)
	{
		this.authenticationManager = authenticationManager;
		this.tokenGenerator = tokenGenerator;
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO)
	{
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = tokenGenerator.generateToken(authentication);
		return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
	}
}
