package com.linkedin.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkedin.dto.LoginRequestDto;
import com.linkedin.dto.SignupRequestDto;
import com.linkedin.dto.UserDto;
import com.linkedin.entity.User;
import com.linkedin.exception.BadRequestException;
import com.linkedin.exception.ResourceNotFoundException;
import com.linkedin.repository.UserRepository;
import com.linkedin.utils.PasswordUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackFor = Throwable.class)
public class AuthService {

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final JwtService jwtService;

	public UserDto signUp(final SignupRequestDto signupRequestDto) {
		signupRequestDto.setEmail(signupRequestDto.getEmail().toLowerCase());
		boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
		if (exists) {
			throw new BadRequestException("User already exists, cannot signup again.");
		}

		User user = modelMapper.map(signupRequestDto, User.class);
		user.setEmail(user.getEmail());
		user.setPassword(PasswordUtil.hashPassword(signupRequestDto.getPassword()));

		User savedUser = userRepository.save(user);
		return modelMapper.map(savedUser, UserDto.class);
	}

	public String login(final LoginRequestDto loginRequestDto) {
		loginRequestDto.setEmail(loginRequestDto.getEmail().toLowerCase());
		User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
				() -> new ResourceNotFoundException("User not found with email: " + loginRequestDto.getEmail()));

		boolean isPasswordMatch = PasswordUtil.checkPassword(loginRequestDto.getPassword(), user.getPassword());

		if (!isPasswordMatch) {
			throw new BadRequestException("Incorrect password");
		}

		return jwtService.generateAccessToken(user);
	}
}
