package com.auth.userauth.service;

import java.time.LocalDateTime;
import java.util.Random;

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

	@Autowired
	private EmailService emailService;

	public User registerUser(RegisterRequest request) {

		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new RuntimeException("Email already exists");
		}

		User user = new User();

		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole("USER");

		String otp = String.valueOf(100000 + new Random().nextInt(900000));

		user.setOtp(otp);
		user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
		user.setVerified(false);

		User savedUser = userRepository.save(user);

		emailService.sendOtp(user.getEmail(), otp);

		return savedUser;
	}

	public String loginUser(LoginRequest request) {

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		if (!user.isVerified()) {
			throw new IllegalArgumentException("Verify Your email before Login");
		}

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

	public String verifyOtp(String email, String otp) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found"));

		if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
			userRepository.delete(user);
			throw new RuntimeException("OTP is expired. Register again");
		}

		if (user.getOtp().equals(otp)) {
			user.setVerified(true);
			user.setOtp(null);
			user.setOtpExpiry(null);
			userRepository.save(user);
			return "Account Verified Successfully";
		}
		throw new RuntimeException("Invalid or Expired OTP");

	}

	public String forgotPassword(String email) {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		String otp = String.valueOf(100000 + new Random().nextInt(900000));

		user.setOtp(otp);
		user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

		userRepository.save(user);

		emailService.sendOtp(user.getEmail(), otp);

		return "OTP sent to email";
	}

	public String resetPassword(String email, String otp, String newPassword) {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("OTP expired");
		}

		if (!user.getOtp().equals(otp)) {
			throw new RuntimeException("Invalid OTP");
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		user.setOtp(null);
		user.setOtpExpiry(null);

		userRepository.save(user);

		return "Password reset successful";
	}

	public String resendOtp(String email) {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		if (user.isVerified()) {
			throw new RuntimeException("User already verified");
		}

		String otp = String.valueOf(100000 + new Random().nextInt(900000));

		user.setOtp(otp);
		user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

		userRepository.save(user);

		emailService.sendOtp(user.getEmail(), otp);

		return "New OTP sent successfully";
	}

}
