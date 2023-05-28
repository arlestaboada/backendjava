package com.arles.backendjava.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.arles.backendjava.services.UserServiceInterface;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    private final UserServiceInterface userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserServiceInterface userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        String url = "/users";
        // Configure AuthenticationManagerBuilder
        final AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);
        final AuthenticationManager authenticationManager = authenticationManagerBuilder.getOrBuild();

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, url)
                .permitAll()
                .anyRequest()
                .authenticated())

                .authenticationManager(authenticationManager)
                .addFilterBefore(getAuthenticationFilter(authenticationManager),
                        UsernamePasswordAuthenticationFilter.class)

                .csrf(csrf -> csrf.disable())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();

    }

    public AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        String url_login = "/users/login";
        final AuthenticationFilter filter = new AuthenticationFilter(url_login, authenticationManager);
        return filter;

    }

}
