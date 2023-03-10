package com.neighborhood.auth.model.payload;

public class AfterLogin {
	private String accessToken;
	private String tokenType = "Bearer Token";
	private String message;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AfterLogin(String accessToken, String message) {
		super();
		this.accessToken = accessToken;
		this.message = message;
	}
}
