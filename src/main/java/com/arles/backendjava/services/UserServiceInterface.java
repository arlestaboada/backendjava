package com.arles.backendjava.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.arles.backendjava.shared.dto.PostDto;
import com.arles.backendjava.shared.dto.UserDto;

public interface UserServiceInterface extends UserDetailsService {

    public UserDto createUser(UserDto user);

    public UserDto getUser(String email);

    public List<PostDto> getUserPots(String email);

}
