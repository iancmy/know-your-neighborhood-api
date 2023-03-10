package com.neighborhood.configuration.properties;

import java.util.ArrayList;

public class OAuth2 {
  private ArrayList<String> authorizedRedirectUris;

  public ArrayList<String> getAuthorizedRedirectUris() {
    return this.authorizedRedirectUris;
  }

  public void setAuthorizedRedirectUris(ArrayList<String> authorizedRedirectUris) {
    this.authorizedRedirectUris = authorizedRedirectUris;
  }
}
