package com.auth.userauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.userauth.config.JwtUtil;
import com.auth.userauth.dto.LoginRequest;
import com.auth.userauth.dto.ProfileResponse;
import com.auth.userauth.dto.RegisterRequest;
import com.auth.userauth.entity.User;
import com.auth.userauth.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public String registerUser(@RequestBody RegisterRequest request) {

		userService.registerUser(request);
		return "User registered successfully";

	}

	@PostMapping("/login")
	public String loginUser(@RequestBody LoginRequest request) {

		return userService.loginUser(request);

	}

	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/profile")
	public ProfileResponse profile() {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.getProfile(email);
	}

	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/admin")
	public String admin() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = userService.getUserByEmail(email);

		if (!user.getRole().equals("ADMIN")) {
			return "Access denied";
		}
		return "welcome Adminuuu";
	}
}
