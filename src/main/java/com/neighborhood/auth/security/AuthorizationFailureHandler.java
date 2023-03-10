package com.neighborhood.auth.security;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.neighborhood.auth.util.CookieUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
        @Autowired
        HttpCookieAuthorizationRequestRepo httpCookieAuthorizationRequestRepo;

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException exception) throws IOException, ServletException {
                String targetUrl = CookieUtils
                                .getCookie(request, HttpCookieAuthorizationRequestRepo.REDIRECT_URI_PARAM_COOKIE_NAME)
                                .map(Cookie::getValue)
                                .orElse(("/"));

                String error = URLEncoder.encode(exception.getLocalizedMessage(), "UTF-8");

                targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                                .queryParam("error", error)
                                .build().toUriString();

                httpCookieAuthorizationRequestRepo.removeAuthorizationRequestCookies(request, response);

                getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
}
