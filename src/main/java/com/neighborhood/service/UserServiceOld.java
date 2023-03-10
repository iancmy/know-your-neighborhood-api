package com.neighborhood.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neighborhood.model.User;
import com.neighborhood.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
@Transactional
public class UserServiceOld {
  @Autowired
  UserRepository userRepository;

  public User findById(String userId) {
    return userRepository.findById(userId).get();
  }

  public User findByEmail(String email) {
    Optional<User> user = userRepository.findAll().stream()
        .filter(u -> u.getEmailAddress().equals(email))
        .findFirst();
    return user.orElse(null);
  }

  public User findByUsername(String username) {
    Optional<User> user = userRepository.findAll().stream()
        .filter(u -> u.getUsername().equals(username))
        .findFirst();
    return user.orElse(null);
  }

  public void newUser(User user) {
    userRepository.save(user);
  }

  // public User authenticate(String email, String password) {
  // return userRepository.authenticate(email, password);
  // }

  public User postAuthenticate(HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();
    String accessToken = "";

    if (cookies != null && cookies.length > 0) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("access_token")) {
          accessToken = cookie.getValue();
        }
      }
    } else {
      accessToken = req.getHeader("UserId");
    }

    return userRepository.findById(accessToken).get();
  }
}
