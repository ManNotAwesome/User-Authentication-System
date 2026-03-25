package com.auth.system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.auth.userauth.config.JwtUtil;
import com.auth.userauth.dto.LoginRequest;
import com.auth.userauth.dto.RegisterRequest;
import com.auth.userauth.entity.User;
import com.auth.userauth.repository.UserRepository;
import com.auth.userauth.service.UserService;

public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@InjectMocks
	private UserService userService;

	public UserServiceTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void loginUserSuccess() {
		LoginRequest request = new LoginRequest();
		request.setEmail("test@test.com");
		request.setPassword("1234");

		User user = new User();
		user.setEmail("test@test.com");
		user.setPassword("encoded");

		when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
		when(passwordEncoder.matches("1234", "encoded")).thenReturn(true);
		when(jwtUtil.generateToken("test@test.com")).thenReturn("mock-token");

		String result = userService.loginUser(request);
		assertEquals("mock-token", result);

	}

	@Test
	void registerUserDuplicateEmail() {
		RegisterRequest request = new RegisterRequest();
		request.setEmail("test@test.com");

		User existingUser = new User();
		existingUser.setEmail("test@test.com");

		when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(existingUser));

		RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
				() -> userService.registerUser(request));

		assertEquals("Email already exists", exception.getMessage());
	}

	@Test
	void loginUserInvalidPassword() {
		LoginRequest request = new LoginRequest();
		request.setEmail("test@test.com");
		request.setPassword("wrong");

		User user = new User();
		user.setEmail("test@test.com");
		user.setPassword("encoded");

		when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
		when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

		IllegalArgumentException exception = org.junit.jupiter.api.Assertions
				.assertThrows(IllegalArgumentException.class, () -> userService.loginUser(request));

		assertEquals("Invalid password", exception.getMessage());
	}

}
