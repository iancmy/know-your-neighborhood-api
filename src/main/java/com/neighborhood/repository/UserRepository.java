package com.neighborhood.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neighborhood.model.User;

public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUsername(String username);

  Optional<User> findByEmailAddress(String emailAddress);

  Boolean existsByUsername(String username);

  Boolean existsByEmailAddress(String emailAddress);
}
