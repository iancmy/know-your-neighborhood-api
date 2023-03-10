package com.neighborhood.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neighborhood.auth.model.annotation.CurrentUser;
import com.neighborhood.auth.service.UserPrincipal;
import com.neighborhood.auth.util.CookieUtils;
import com.neighborhood.model.User;
import com.neighborhood.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserPrivateController {
  @Autowired
  private UserService userService;

  @GetMapping("/account")
  public ResponseEntity<?> getAccount(@CurrentUser UserPrincipal userPrincipal) {
    try {
      User user = userService.getById(userPrincipal.getUserId());

      if (user == null) {
        return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
      }

      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Error getting user account!", HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/account")
  public ResponseEntity<?> updateAccount(@CurrentUser UserPrincipal userPrincipal, @RequestBody User user) {
    Map<String, Object> res = new HashMap<>();

    try {
      User userToUpdate = userService.getById(userPrincipal.getUserId());

      if (userToUpdate == null) {
        res.put("isSuccessful", false);
        res.put("error", "User not found!");

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
      }

      userToUpdate.setFirstName(user.getFirstName());
      userToUpdate.setLastName(user.getLastName());
      userToUpdate.setEmailAddress(user.getEmailAddress());
      userToUpdate.setUsername(user.getUsername());
      userToUpdate.setContactNumber(user.getContactNumber());

      userService.updateUser(userToUpdate);

      res.put("isSuccessful", true);
      res.put("successMessage", "Profile has been updated successfully!");
      return new ResponseEntity<>(res, HttpStatus.OK);
    } catch (Exception e) {

      res.put("isSuccessful", false);
      res.put("error", "Error updating user account!");
      return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/auth")
  public ResponseEntity<?> authenticate(@CurrentUser UserPrincipal userPrincipal, HttpServletRequest request,
      HttpServletResponse response) {
    Map<String, Object> res = new HashMap<>();

    try {
      User user = userService.getById(userPrincipal.getUserId());

      if (user == null) {
        res.put("successful", false);
        res.put("error", "Authentication failed! Please login again.");

        return new ResponseEntity<>("User not found!", HttpStatus.FORBIDDEN);
      }

      // get the user's token from header
      String token = request.getHeader("Authorization").substring(7);

      // add token to cookies
      int cookieMaxAge = 60 * 60 * 24; // 1 day
      CookieUtils.addCookie(response, "access_token", token, cookieMaxAge);

      res.put("successful", true);
      res.put("message", "Authentication successful!");

      return new ResponseEntity<>(res, HttpStatus.OK);
    } catch (Exception e) {

      res.put("successful", false);
      res.put("error", "Authentication failed! Please login again.");
      return new ResponseEntity<>("Error getting user account!", HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(@CurrentUser UserPrincipal userPrincipal, HttpServletRequest request,
      HttpServletResponse response) {
    Map<String, Object> res = new HashMap<>();

    try {
      User user = userService.getById(userPrincipal.getUserId());

      if (user == null) {
        res.put("successful", false);
        res.put("error", "Logout failed! Please login again.");

        return new ResponseEntity<>("User not found!", HttpStatus.FORBIDDEN);
      }

      // add token to cookies
      int cookieMaxAge = 0; // 1 day
      CookieUtils.addCookie(response, "access_token", "", cookieMaxAge);

      res.put("successful", true);
      res.put("message", "Logout successful!");

      return new ResponseEntity<>(res, HttpStatus.OK);
    } catch (Exception e) {

      res.put("successful", false);
      res.put("error", "Logout failed! Please login again.");
      return new ResponseEntity<>("Error getting user account!", HttpStatus.BAD_REQUEST);
    }
  }
}
