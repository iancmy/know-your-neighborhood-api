package com.neighborhood.auth.model;

import java.util.Map;

import com.neighborhood.auth.error.OAuthException;
import com.neighborhood.model.AuthProvider;

public class OAuth2UsersFactory {
	public static OAuth2Users getInstance(String registerId, Map<String, Object> attributes)
			throws OAuthException {
		if (registerId.equalsIgnoreCase(AuthProvider.google.toString())) {
			return new GoogleOAuth2User(attributes);
		} else if (registerId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
			return new FacebookOAuth2User(attributes);
		} else {
			throw new OAuthException("Login with" + registerId + "is currently not supported!");
		}
	}
}
