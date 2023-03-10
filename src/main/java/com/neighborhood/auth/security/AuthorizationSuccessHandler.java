package com.neighborhood.auth.security;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.neighborhood.auth.jwt.TokenProvider;
import com.neighborhood.auth.util.CookieUtils;
import com.neighborhood.configuration.properties.AppProperties;
import com.neighborhood.error.BadRequestException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private TokenProvider tokenProvider;

    private AppProperties appProperties;

    private HttpCookieAuthorizationRequestRepo httpCookieAuthorizationRequestRepo;

    AuthorizationSuccessHandler(TokenProvider tokenProvider, AppProperties appProperties,
            HttpCookieAuthorizationRequestRepo httpCookieAuthorizationRequestRepo) {
        this.tokenProvider = tokenProvider;
        this.appProperties = appProperties;
        this.httpCookieAuthorizationRequestRepo = httpCookieAuthorizationRequestRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request,
                HttpCookieAuthorizationRequestRepo.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException(
                    "Sorry! We've got an unauthorized Redirect URI and cannot proceed with the authentication.");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String token = tokenProvider.createToken(authentication);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieAuthorizationRequestRepo.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        ArrayList<String> authorizedRedirectUris = appProperties.getOauth2().getAuthorizedRedirectUris();

        // If the client-redirect-uri is present in the authorized-redirect-uris list,
        // then the URI is valid

        for (String authorizedRedirectUri : authorizedRedirectUris) {
            URI authorizedURI = URI.create(authorizedRedirectUri);

            if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                    && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                return true;
            }
        }

        return false;
    }
}
