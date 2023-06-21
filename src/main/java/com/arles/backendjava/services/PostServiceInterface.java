package com.arles.backendjava.services;

import java.util.List;

import com.arles.backendjava.shared.dto.PostCreationDto;
import com.arles.backendjava.shared.dto.PostDto;

public interface PostServiceInterface {

    public PostDto createPost(PostCreationDto post);

    public List<PostDto> getLastPosts();

}
