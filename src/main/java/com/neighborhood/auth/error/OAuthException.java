package com.neighborhood.auth.error;

import org.springframework.security.core.AuthenticationException;

public class OAuthException extends AuthenticationException {
  private static final long serialVersionUID = 1L;

  public OAuthException(String msg) {
    super(msg);
  }
}
