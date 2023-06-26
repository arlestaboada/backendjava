package com.arles.backendjava.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arles.backendjava.models.responses.PostRest;
import com.arles.backendjava.models.requests.UserDetailsRequestModel;
import com.arles.backendjava.models.responses.UserRest;
import com.arles.backendjava.services.UserServiceInterface;
import com.arles.backendjava.shared.dto.PostDto;
import com.arles.backendjava.shared.dto.UserDto;

@RestController
@RequestMapping("/users") // localhost:8080/users
public class UserController {
    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public UserRest getUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String email = authentication.getPrincipal().toString();
        UserDto userDto = userService.getUser(email);

        UserRest userRest = modelMapper.map(userDto, UserRest.class);

        return userRest;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        UserRest userToReturn = new UserRest();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userDetails, userDto);
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, userToReturn);

        return userToReturn;
    }

    @GetMapping("/posts") // localhost:8080/users/post
    public List<PostRest> getPots() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String email = authentication.getPrincipal().toString();
        List<PostDto> posts = userService.getUserPots(email);
        List<PostRest> postRests = new ArrayList<>();

        for (PostDto post : posts) {
            PostRest postRest = modelMapper.map(post, PostRest.class);

            if (postRest.getExpiresAt().compareTo(
                    new Date(System.currentTimeMillis())) < 0) {

                postRest.setExpired(true);
            }
            postRests.add(postRest);

        }

        return postRests;
    }

}
