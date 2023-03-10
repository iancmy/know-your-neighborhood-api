package com.neighborhood.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neighborhood.model.Store;
import com.neighborhood.service.StoreService;

@RestController
@RequestMapping("/api/public/store")
public class StoreController {
  @Autowired
  StoreService storeService;

  @GetMapping
  public ResponseEntity<Map<String, Object>> searchStore(@RequestParam("query") String query) {
    Map<String, Object> res = new HashMap<>();
    boolean isSuccessful = false;

    try {
      List<Store> stores = storeService.searchStore(query);
      isSuccessful = true;

      if (stores.size() < 1) {
        res.put("isSuccessful", isSuccessful);
        res.put("stores", stores);
        res.put("error", "No stores matched with that query.");

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
      }

      res.put("stores", stores);
      res.put("isSuccessful", isSuccessful);

      return new ResponseEntity<>(res, HttpStatus.OK);
    } catch (Error e) {
      res.put("isSuccessful", isSuccessful);
      res.put("error", e);

      return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/all")
  public ResponseEntity<Map<String, Object>> getAllStores() {
    Map<String, Object> res = new HashMap<>();
    boolean isSuccessful = false;

    try {
      List<Store> stores = storeService.findAll();
      isSuccessful = true;

      if (stores.size() < 1) {
        res.put("isSuccessful", isSuccessful);
        res.put("stores", stores);
        res.put("message", "No stores available.");

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
      }

      res.put("stores", stores);
      res.put("isSuccessful", isSuccessful);

      return new ResponseEntity<>(res, HttpStatus.OK);
    } catch (Error e) {
      res.put("isSuccessful", isSuccessful);
      res.put("error", e);

      return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{storeId}")
  public ResponseEntity<Map<String, Object>> findById(@PathVariable("storeId") String storeId) {
    Map<String, Object> res = new HashMap<>();
    boolean isSuccessful = false;

    try {
      Store store = storeService.findById(storeId);
      isSuccessful = true;

      if (store == null) {
        res.put("isSuccessful", isSuccessful);
        res.put("storeDetails", store);
        res.put("message", "No store found with that ID.");

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
      }

      res.put("storeDetails", store);
      res.put("isSuccessful", isSuccessful);

      return new ResponseEntity<>(res, HttpStatus.OK);
    } catch (Error e) {
      res.put("isSuccessful", isSuccessful);
      res.put("error", e);

      return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
