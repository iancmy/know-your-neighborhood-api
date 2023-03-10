package com.neighborhood.configuration.properties;

public class Auth {
  private String tokenSecret;
  private long tokenExpireMs;

  public String getTokenSecret() {
    return this.tokenSecret;
  }

  public void setTokenSecret(String tokenSecret) {
    this.tokenSecret = tokenSecret;
  }

  public long getTokenExpireMs() {
    return this.tokenExpireMs;
  }

  public void setTokenExpireMs(long tokenExpireMs) {
    this.tokenExpireMs = tokenExpireMs;
  }
}
