package com.auth.userauth.dto;

public class ProfileResponse {

	private String name;

	private String email;

	private String role;

	public ProfileResponse(String name, String email, String role) {
		this.name = name;
		this.email = email;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getRole() {
		return role;
	}

}
