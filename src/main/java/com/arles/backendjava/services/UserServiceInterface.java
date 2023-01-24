package com.arles.backendjava.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.arles.backendjava.shared.dto.UserDto;

public interface UserServiceInterface extends UserDetailsService {

    public UserDto createUser(UserDto user);

}
