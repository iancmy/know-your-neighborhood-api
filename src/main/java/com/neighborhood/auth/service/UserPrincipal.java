package com.neighborhood.auth.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.neighborhood.model.User;

public class UserPrincipal implements UserDetails, OAuth2User {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String emailAddress;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;

	public UserPrincipal(String userId, String emailAddress, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.userId = userId;
		this.emailAddress = emailAddress;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserPrincipal createUser(User user) {
		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

		UserPrincipal userPrincipal = new UserPrincipal(user.getUserId(), user.getEmailAddress(), user.getUsername(),
				user.getPassword(), authorities);

		return userPrincipal;
	}

	public static UserPrincipal createUser(User user, Map<String, Object> attributes) {
		UserPrincipal userPrincipal = UserPrincipal.createUser(user);
		userPrincipal.setAttributes(attributes);

		return userPrincipal;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public String getUsername() {
		return this.emailAddress;
	}

	public String setUsername(String username) {
		this.username = username;

		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getName() {
		return this.userId;
	}
}
