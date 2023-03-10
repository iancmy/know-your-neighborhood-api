package com.neighborhood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neighborhood.auth.jwt.TokenProvider;
import com.neighborhood.auth.model.payload.AfterLogin;
import com.neighborhood.auth.model.payload.AfterSignup;
import com.neighborhood.auth.model.payload.Login;
import com.neighborhood.auth.model.payload.Signup;
import com.neighborhood.model.AuthProvider;
import com.neighborhood.model.User;
import com.neighborhood.service.UserService;

@RestController
@RequestMapping("/api/public/user")
public class UserController {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Login loginCredentials) {
    String username = loginCredentials.getEmailAddress();

    try {
      if (username == null || username.isEmpty()) {
        if (loginCredentials.getUsername() == null || loginCredentials.getUsername().isEmpty()) {
          return new ResponseEntity<>(new AfterLogin(null, "Email address or username must be provided!"),
              HttpStatus.BAD_REQUEST);
        }

        username = userService.getByUsername(loginCredentials.getUsername()).getEmailAddress();
      }

      Authentication auth = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, loginCredentials.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(auth);

      String token = tokenProvider.createToken(auth);

      return new ResponseEntity<>(new AfterLogin(token, "Log in successful!"), HttpStatus.OK);
    } catch (AuthenticationException e) {
      return new ResponseEntity<>(new AfterLogin(null, "Invalid username and/or password!"), HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody Signup signupCredentials) {
    if (userService.existsByEmailAddress(signupCredentials.getEmailAddress())) {
      return new ResponseEntity<>(new AfterSignup(false, "Email address already in use!"), HttpStatus.BAD_REQUEST);
    }

    if (userService.existsByUsername(signupCredentials.getUsername())) {
      return new ResponseEntity<>(new AfterSignup(false, "Username already in use!"), HttpStatus.BAD_REQUEST);
    }

    try {
      User newUser = new User();

      newUser.setFirstName(signupCredentials.getFirstName());
      newUser.setLastName(signupCredentials.getLastName());
      newUser.setEmailAddress(signupCredentials.getEmailAddress());
      newUser.setUsername(signupCredentials.getUsername());
      newUser.setContactNumber(signupCredentials.getContactNumber());

      newUser.setProvider(AuthProvider.local);

      newUser.setPassword(passwordEncoder.encode(signupCredentials.getPassword()));

      userService.save(newUser);

      return new ResponseEntity<>(new AfterSignup(true, "Sign up successful!"), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new AfterSignup(false, "Sign up failed!"), HttpStatus.BAD_REQUEST);
    }
  }
}
