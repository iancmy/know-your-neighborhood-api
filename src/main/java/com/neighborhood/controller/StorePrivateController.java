package com.neighborhood.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neighborhood.auth.model.annotation.CurrentUser;
import com.neighborhood.auth.service.UserPrincipal;
import com.neighborhood.model.Store;
import com.neighborhood.model.User;
import com.neighborhood.service.StoreService;
import com.neighborhood.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/store")
public class StorePrivateController {
  @Autowired
  StoreService storeService;

  @Autowired
  UserService userService;

  @PostMapping
  public ResponseEntity<Map<String, Object>> newStore(@CurrentUser UserPrincipal userPrincipal,
      @RequestBody Store newStore, HttpServletRequest request,
      HttpServletResponse response) {
    Map<String, Object> res = new HashMap<>();
    boolean isSuccessful = false;

    try {
      User authenticatedUser = userService.getById(userPrincipal.getUserId());

      if (authenticatedUser == null) {
        res.put("isSuccessful", isSuccessful);
        res.put("error", "Unauthenticated user! Please try to login again.");

        return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
      }

      try {
        newStore.setUser(authenticatedUser);
        storeService.newStore(newStore);
        isSuccessful = true;

        res.put("storeDetails", newStore);
        res.put("isSuccessful", isSuccessful);
        res.put("successMessage", "Store has been saved successfully!");
      } catch (Error e) {
        res.put("isSuccessful", isSuccessful);
        res.put("error", e);

        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
      }

      return new ResponseEntity<>(res, HttpStatus.OK);
    } catch (Error e) {
      res.put("isSuccessful", isSuccessful);
      res.put("error", e);

      return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{storeId}")
  public ResponseEntity<Map<String, Object>> updateStore(@CurrentUser UserPrincipal userPrincipal,
      @RequestBody Store updatedStore, @PathVariable("storeId") String storeId) {
    Map<String, Object> res = new HashMap<>();
    boolean isSuccessful = false;

    try {
      User authenticatedUser = userService.getById(userPrincipal.getUserId());

      if (authenticatedUser == null) {
        res.put("isSuccessful", isSuccessful);
        res.put("error", "Unauthenticated user! Please try to login again.");

        return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
      }

      try {
        Store store = storeService.findById(storeId);

        if (store == null) {
          res.put("isSuccessful", isSuccessful);
          res.put("error", "Store not found!");

          return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }

        if (store.getUser().getUserId() != authenticatedUser.getUserId()) {
          res.put("isSuccessful", isSuccessful);
          res.put("error", "You are not authorized to update this store!");

          return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        store.setStoreName(updatedStore.getStoreName());
        store.setLocation(updatedStore.getLocation());

        storeService.updateStore(store);
        isSuccessful = true;

        res.put("isSuccessful", isSuccessful);
        res.put("successMessage", "Store has been updated successfully!");
      } catch (Error e) {
        res.put("isSuccessful", isSuccessful);
        res.put("error", e);

        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
      }

      return new ResponseEntity<>(res, HttpStatus.OK);
    } catch (Error e) {
      res.put("isSuccessful", isSuccessful);
      res.put("error", e);

      return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{storeId}")
  public ResponseEntity<Map<String, Object>> deleteStore(@CurrentUser UserPrincipal userPrincipal,
      @PathVariable("storeId") String storeId) {
    Map<String, Object> res = new HashMap<>();
    boolean isSuccessful = false;

    try {
      User authenticatedUser = userService.getById(userPrincipal.getUserId());

      if (authenticatedUser == null) {
        res.put("isSuccessful", isSuccessful);
        res.put("error", "Unauthenticated user! Please try to login again.");

        return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
      }

      try {
        Store store = storeService.findById(storeId);

        if (store == null) {
          res.put("isSuccessful", isSuccessful);
          res.put("error", "Store not found!");

          return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }

        if (store.getUser().getUserId() != authenticatedUser.getUserId()) {
          res.put("isSuccessful", isSuccessful);
          res.put("error", "You are not authorized to delete this store!");

          return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        storeService.deleteStore(store.getStoreId());
        isSuccessful = true;

        res.put("isSuccessful", isSuccessful);
        res.put("successMessage", "Store has been deleted successfully!");
      } catch (Error e) {
        res.put("isSuccessful", isSuccessful);
        res.put("error", e);

        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
      }

      return new ResponseEntity<>(res, HttpStatus.OK);
    } catch (Error e) {
      res.put("isSuccessful", isSuccessful);
      res.put("error", e);

      return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
