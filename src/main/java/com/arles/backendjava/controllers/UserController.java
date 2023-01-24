package com.arles.backendjava.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arles.backendjava.models.requests.UserDetailsRequestModel;
import com.arles.backendjava.models.responses.UserRest;
import com.arles.backendjava.services.UserServiceInterface;
import com.arles.backendjava.shared.dto.UserDto;

@RestController
@RequestMapping("/users") // localhost:8080/users
public class UserController {
    @Autowired
    UserServiceInterface userService;

    @GetMapping
    public String getUser() {
        return "get user details";
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

}
