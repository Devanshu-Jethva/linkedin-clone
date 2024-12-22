package com.linkedin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkedin.dto.LoginRequestDto;
import com.linkedin.dto.SignupRequestDto;
import com.linkedin.dto.UserDto;
import com.linkedin.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<UserDto> signUp(@RequestBody final SignupRequestDto signupRequestDto) {
		UserDto userDto = authService.signUp(signupRequestDto);
		return new ResponseEntity<>(userDto, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody final LoginRequestDto loginRequestDto) {
		String token = authService.login(loginRequestDto);
		return ResponseEntity.ok(token);
	}
}
