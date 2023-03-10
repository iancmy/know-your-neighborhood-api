package com.neighborhood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neighborhood.auth.service.UserPrincipal;
import com.neighborhood.error.ResourceNotFoundException;
import com.neighborhood.model.User;
import com.neighborhood.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
		User user = userRepo.findByEmailAddress(emailAddress)
				.orElseThrow(() -> new UsernameNotFoundException("This email cannot be found" + emailAddress));

		return UserPrincipal.createUser(user);
	}

	// Used for JWT Authentication Filter
	public UserDetails loadUserById(String userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

		return UserPrincipal.createUser(user);
	}

	public void save(User user) {
		userRepo.save(user);
	}

	public void updateUser(User user) {
		userRepo.save(user);
	}

	public User getById(String userId) {
		return userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
	}

	public User getByEmailAddress(String emailAddress) {
		return userRepo.findByEmailAddress(emailAddress)
				.orElseThrow(() -> new ResourceNotFoundException("User", "emailAddress", emailAddress));
	}

	public User getByUsername(String username) {
		return userRepo.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
	}

	public Boolean existsByUsername(String username) {
		return userRepo.existsByUsername(username);
	}

	public Boolean existsByEmailAddress(String emailAddress) {
		return userRepo.existsByEmailAddress(emailAddress);
	}
}
