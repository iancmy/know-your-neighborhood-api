package com.neighborhood.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
  private Auth auth;
  private OAuth2 oauth2;

  public Auth getAuth() {
    return this.auth;
  }

  public OAuth2 getOauth2() {
    return this.oauth2;
  }

  public void setAuth(Auth auth) {
    this.auth = auth;
  }

  public void setOauth2(OAuth2 oauth2) {
    this.oauth2 = oauth2;
  }
}
