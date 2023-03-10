package com.neighborhood.auth.model;

import java.util.Map;

public class GoogleOAuth2User extends OAuth2Users {

	public GoogleOAuth2User(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getUserId() {
		return (String) attributes.get("sub");
	}

	@Override
	public String getName() {
		return (String) attributes.get("name");
	}

	@Override
	public String getEmailAddress() {
		return (String) attributes.get("email");
	}

	@Override
	public String getImageUrl() {
		return (String) attributes.get("picture");
	}
}
