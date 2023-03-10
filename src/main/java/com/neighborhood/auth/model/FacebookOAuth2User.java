package com.neighborhood.auth.model;

import java.util.Map;

public class FacebookOAuth2User extends OAuth2Users {

	public FacebookOAuth2User(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getUserId() {
		return (String) attributes.get("id");
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
		if (!attributes.containsKey("picture") || !(attributes.get("picture") instanceof Map)) {
			return null;
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("picture");

		if (!pictureObj.containsKey("data") || !(pictureObj.get("data") instanceof Map)) {
			return null;
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> dataObj = (Map<String, Object>) pictureObj.get("data");

		if (!dataObj.containsKey("url") || !(dataObj.get("url") instanceof String)) {
			return null;
		}

		return (String) dataObj.get("url");
	}
}
