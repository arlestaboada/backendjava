package com.arles.backendjava.services;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arles.backendjava.entities.ExposureEntity;
import com.arles.backendjava.entities.PostEntity;
import com.arles.backendjava.entities.UserEntity;
import com.arles.backendjava.repositories.ExposureRepository;
import com.arles.backendjava.repositories.PostRepository;
import com.arles.backendjava.repositories.UserRepository;
import com.arles.backendjava.shared.dto.PostCreationDto;
import com.arles.backendjava.shared.dto.PostDto;

@Service
public class PostService implements PostServiceInterface {

        @Autowired
        PostRepository postRepository;

        @Autowired
        UserRepository userRepository;

        @Autowired
        ExposureRepository exposureRepository;

        @Override
        public PostDto createPost(PostCreationDto post) {

                UserEntity userEntity = userRepository
                                .findByEmail(post.getUserEmail());

                ExposureEntity exposureEntity = exposureRepository
                                .findById(post.getExposureId());
                PostEntity postEntity = new PostEntity();
                postEntity.setUser(userEntity);
                postEntity.setExposure(exposureEntity);
                postEntity.setPost_id(UUID.randomUUID().toString());
                postEntity.setTitle(post.getTitle());
                postEntity.setContent(post.getContent());
                postEntity.setExpiresAt(new Date(System.currentTimeMillis()
                                + (post.getExpirationTime() * 60000)));

                PostEntity createdPost = postRepository.save(postEntity);

                ModelMapper mapper = new ModelMapper();
                PostDto postToReturn = mapper.map(createdPost, PostDto.class);

                return postToReturn;
        }

}
