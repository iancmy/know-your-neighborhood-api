package com.neighborhood.auth.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.neighborhood.auth.service.UserPrincipal;
import com.neighborhood.configuration.properties.AppProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenProvider {
    private AppProperties appProperties;

    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // Define expire date for token key
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpireMs());

        // Use Jwts.builder method
        // HS512 is Signature Algorithm to generate token, it requires a key with
        // minimum size of 512
        SecretKey secretKey = Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes());

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(userPrincipal.getUserId());
        jwtBuilder.setIssuedAt(now);
        jwtBuilder.setExpiration(expiryDate);
        jwtBuilder.signWith(secretKey, SignatureAlgorithm.HS512);

        return jwtBuilder.compact();
    }

    // To parse Jwt String
    public String getUserIdFromToken(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(appProperties.getAuth().getTokenSecret().getBytes())
                .build();

        Claims claims = parser.parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    // Validate Token
    public boolean validateToken(String authToken) {
        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(appProperties.getAuth().getTokenSecret().getBytes())
                    .build();

            parser.parseClaimsJws(authToken);

            return true;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return false;
    }
}
