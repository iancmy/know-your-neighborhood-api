package com.neighborhood.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Store {
  @Id
  private String storeId;

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  @JsonIgnore
  private User user;

  @Column(nullable = false)
  private String storeName;

  @Column(nullable = false)
  private String location;

  public Store() {
    this.storeId = UUID.randomUUID().toString();
  }

  public Store(String storeName, String location) {
    this.storeId = UUID.randomUUID().toString();
    this.storeName = storeName;
    this.location = location;
  }

  public String getStoreId() {
    return this.storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getStoreName() {
    return this.storeName;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
