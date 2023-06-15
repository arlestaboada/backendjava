package com.arles.backendjava.controllers;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arles.backendjava.models.requests.PostCreateRequestModel;
import com.arles.backendjava.models.responses.PostRest;
import com.arles.backendjava.services.PostServiceInterface;
import com.arles.backendjava.shared.dto.PostCreationDto;
import com.arles.backendjava.shared.dto.PostDto;

@RestController
@RequestMapping("/posts") // localhost:8080/posts
public class PostController {

    @Autowired
    PostServiceInterface postService;

    @PostMapping
    private PostRest createPost(@RequestBody PostCreateRequestModel createRequestModel) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String email = authentication.getPrincipal().toString();

        ModelMapper mapper = new ModelMapper();

        PostCreationDto postCreationDto = mapper.map(
                createRequestModel,
                PostCreationDto.class);
        postCreationDto.setUserEmail(email);

        PostDto postDto = postService.createPost(postCreationDto);
        PostRest postToReturn = mapper.map(postDto, PostRest.class);

        if (postToReturn.getExpiresAt().compareTo(
                new Date(System.currentTimeMillis())) < 0) {

            postToReturn.setExpired(true);
        }

        return postToReturn;

    }

}
