package com.neighborhood.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class User {
  @Id
  private String userId;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = true)
  private String lastName;

  @Column(nullable = true)
  private String contactNumber;

  @Column(nullable = false, unique = true)
  private String emailAddress;

  @Column(nullable = true, unique = true)
  private String username;

  @JsonIgnore
  @Column(nullable = false)
  private String password;

  private String imageUrl;

  @NotNull
  @Enumerated(EnumType.STRING)
  private AuthProvider provider;

  private String providerId;

  @OneToMany(mappedBy = "user")
  private List<Store> stores;

  public User() {
    this.userId = UUID.randomUUID().toString();
  }

  public User(String firstName, String lastName, String contactNumber, String emailAddress, String username,
      String password) {
    this.userId = UUID.randomUUID().toString();
    this.firstName = firstName;
    this.lastName = lastName;
    this.contactNumber = contactNumber;
    this.emailAddress = emailAddress;
    this.username = username;
    this.password = password;
    this.stores = new ArrayList<>();
  }

  public User(String firstName, String lastName, String contactNumber, String emailAddress,
      String username, String password, AuthProvider provider, String providerId) {
    this.userId = UUID.randomUUID().toString();
    this.firstName = firstName;
    this.lastName = lastName;
    this.contactNumber = contactNumber;
    this.emailAddress = emailAddress;
    this.username = username;
    this.password = password;
    this.provider = provider;
    this.providerId = providerId;
    this.stores = new ArrayList<>();
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getContactNumber() {
    return this.contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public String getEmailAddress() {
    return this.emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getImageUrl() {
    return this.imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public AuthProvider getProvider() {
    return this.provider;
  }

  public void setProvider(AuthProvider provider) {
    this.provider = provider;
  }

  public String getProviderId() {
    return this.providerId;
  }

  public void setProviderId(String providerId) {
    this.providerId = providerId;
  }

  public List<Store> getStores() {
    return this.stores;
  }

  public void setStores(List<Store> stores) {
    this.stores = stores;
  }

}
