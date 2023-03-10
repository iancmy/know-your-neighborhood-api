package com.neighborhood.auth.model.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class Login {
	@Email
	@NotBlank
	private String emailAddress;

	private String username;

	@NotBlank
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
