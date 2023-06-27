package com.arles.backendjava.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arles.backendjava.models.requests.PostCreateRequestModel;
import com.arles.backendjava.models.responses.PostRest;
import com.arles.backendjava.models.responses.operationStatusModel;
import com.arles.backendjava.services.PostServiceInterface;
import com.arles.backendjava.services.UserServiceInterface;
import com.arles.backendjava.shared.dto.PostCreationDto;
import com.arles.backendjava.shared.dto.PostDto;
import com.arles.backendjava.shared.dto.UserDto;

@RestController
@RequestMapping("/posts") // localhost:8080/posts
public class PostController {

    @Autowired
    PostServiceInterface postService;

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper mapper;

    @PostMapping
    private PostRest createPost(@RequestBody PostCreateRequestModel createRequestModel) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String email = authentication.getPrincipal().toString();

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

    @GetMapping(path = "/last") // localhost:8080/posts/last
    public List<PostRest> lastPosts() {

        List<PostDto> postDtos = postService.getLastPosts();

        List<PostRest> postRests = new ArrayList<>();

        for (PostDto postDto : postDtos) {

            PostRest postRest = mapper.map(postDto, PostRest.class);

            postRests.add(postRest);

        }

        return postRests;

    }

    @GetMapping(path = { "/{id}" }) // localhost/posts/uuid
    public PostRest getPost(@PathVariable String id) {
        PostDto post = postService.getPost(id);
        PostRest postRest = mapper.map(post, PostRest.class);

        if (postRest.getExpiresAt().compareTo(
                new Date(System.currentTimeMillis())) < 0) {

            postRest.setExpired(true);
        }
        // validar si el post es privado o si el post ya expiro
        if (postRest.getExposure().getId() == 1 ||
                postRest.getExpired()) {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            UserDto user = userService.getUser(authentication.getPrincipal().toString());

            if (user.getId() != post.getUser().getId()) {

                throw new RuntimeException("No tienes permisos para realizar esta acción");
            }

        }

        return postRest;

    }

    @DeleteMapping("/{id}")
    public operationStatusModel deletePost(@PathVariable String id) {

        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        UserDto user = userService.getUser(authentication.getPrincipal().toString());
        operationStatusModel operationStatusModel = new operationStatusModel();
        operationStatusModel.setName("Delete");
        postService.deletePost(id, user.getId());
        operationStatusModel.setResult("Success");

        return operationStatusModel;

    }

    @PutMapping("/{id}")
    public PostRest updatePost(
            @RequestBody PostCreateRequestModel createRequestModel,
            @PathVariable String id) {

        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        UserDto user = userService.getUser(authentication.getPrincipal().toString());

        PostCreationDto postUpdateDto = mapper.map(createRequestModel, PostCreationDto.class);

        PostDto postDto = postService.updatePost(id, user.getId(), postUpdateDto);

        PostRest updatePost = mapper.map(postDto, PostRest.class);

        return updatePost;
    }

}
