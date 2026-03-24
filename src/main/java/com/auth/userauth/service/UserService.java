package com.auth.userauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.userauth.config.JwtUtil;
import com.auth.userauth.dto.LoginRequest;
import com.auth.userauth.dto.ProfileResponse;
import com.auth.userauth.dto.RegisterRequest;
import com.auth.userauth.entity.User;
import com.auth.userauth.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User registerUser(RegisterRequest request) {

		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new RuntimeException("Email already exists");
		}

		User user = new User();

		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole("USER");

		return userRepository.save(user);
	}

	public String loginUser(LoginRequest request) {

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			return jwtUtil.generateToken(user.getEmail());
		} else {
			throw new IllegalArgumentException("Invalid password");
		}
	}

	public ProfileResponse getProfile(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		return new ProfileResponse(user.getName(), user.getEmail(), user.getRole());
	}

	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
	}

}
