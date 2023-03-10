package com.neighborhood.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.neighborhood.auth.jwt.TokenAuthenticationFilter;
import com.neighborhood.auth.security.AuthEntryPoint;
import com.neighborhood.auth.security.AuthorizationFailureHandler;
import com.neighborhood.auth.security.AuthorizationSuccessHandler;
import com.neighborhood.auth.security.HttpCookieAuthorizationRequestRepo;
import com.neighborhood.auth.service.OAuth2UsersServiceImpl;
import com.neighborhood.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {
  @Autowired
  private UserService userService;

  @Autowired
  private OAuth2UsersServiceImpl oAuth2UsersServiceImpl;

  @Autowired
  private AuthorizationSuccessHandler authorizationSuccessHandler;

  @Autowired
  private AuthorizationFailureHandler authorizationFailureHandler;

  @Autowired
  private AuthEntryPoint authEntryPoint;

  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);

    auth.userDetailsService(userService).passwordEncoder(passwordEncoder());

    return auth.build();
  }

  @Bean
  public HttpCookieAuthorizationRequestRepo cookieAuthorizationRequestRepo() {
    return new HttpCookieAuthorizationRequestRepo();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .csrf()
        .disable()
        .formLogin()
        .disable()
        .httpBasic()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers(
            "/",
            "/error",
            "/favicon.ico",
            "/*/*.png",
            "/*/*.gif",
            "/*/*.svg",
            "/*/*.jpg",
            "/*/*.html",
            "/*/*.css",
            "/*/*.js")
        .permitAll()
        .requestMatchers(
            "/oauth2/*/*",
            "/api/public/*",
            "/api/public/*/*")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .oauth2Login()
        .authorizationEndpoint()
        .baseUri("/oauth2/authorize")
        .authorizationRequestRepository(cookieAuthorizationRequestRepo())
        .and()
        .redirectionEndpoint()
        .baseUri("/oauth2/callback/*")
        .and()
        .userInfoEndpoint()
        .userService(oAuth2UsersServiceImpl)
        .and()
        .successHandler(authorizationSuccessHandler)
        .failureHandler(authorizationFailureHandler)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(authEntryPoint);

    http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
