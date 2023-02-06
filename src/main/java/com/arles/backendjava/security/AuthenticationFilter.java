package com.arles.backendjava.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.arles.backendjava.models.requests.UserLoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public AuthenticationFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url, HttpMethod.POST.name()));
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {

            UserLoginRequestModel userModel = new ObjectMapper().readValue(request.getInputStream(),
                    UserLoginRequestModel.class);

            return this.getAuthenticationManager()
                    .authenticate(new UsernamePasswordAuthenticationToken(userModel.getEmail(),
                            userModel.getPassword(),
                            new ArrayList<>()));

        } catch (IOException e) {

            throw new RuntimeException(e);

        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication)
            throws IOException, ServletException {

        String userName = ((User) authentication.getPrincipal()).getUsername();
        String token = Jwts.builder().setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_DATE))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET).compact();

        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

    }

}
