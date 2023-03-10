package com.neighborhood.auth.model;

import java.util.Map;

public abstract class OAuth2Users {
	protected Map<String, Object> attributes;

	public OAuth2Users(Map<String, Object> attributes) {
		super();
		this.attributes = attributes;
	}

	public abstract String getUserId();

	public abstract String getName();

	public abstract String getEmailAddress();

	public abstract String getImageUrl();
}
