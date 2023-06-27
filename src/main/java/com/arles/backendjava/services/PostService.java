package com.arles.backendjava.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

        public PostRepository getPostRepository() {
                return this.postRepository;
        }

        public void setPostRepository(PostRepository postRepository) {
                this.postRepository = postRepository;
        }

        public UserRepository getUserRepository() {
                return this.userRepository;
        }

        public void setUserRepository(UserRepository userRepository) {
                this.userRepository = userRepository;
        }

        public ExposureRepository getExposureRepository() {
                return this.exposureRepository;
        }

        public void setExposureRepository(ExposureRepository exposureRepository) {
                this.exposureRepository = exposureRepository;
        }

        public ModelMapper getMapper() {
                return this.mapper;
        }

        public void setMapper(ModelMapper mapper) {
                this.mapper = mapper;
        }

        @Autowired
        PostRepository postRepository;

        @Autowired
        UserRepository userRepository;

        @Autowired
        ExposureRepository exposureRepository;

        @Autowired
        ModelMapper mapper;

        @Override
        public PostDto createPost(PostCreationDto post) {

                UserEntity userEntity = userRepository
                                .findByEmail(post.getUserEmail());

                ExposureEntity exposureEntity = exposureRepository
                                .findById(post.getExposureId());
                PostEntity postEntity = new PostEntity();
                postEntity.setUser(userEntity);
                postEntity.setExposure(exposureEntity);
                postEntity.setPostId(UUID.randomUUID().toString());
                postEntity.setTitle(post.getTitle());
                postEntity.setContent(post.getContent());
                postEntity.setExpiresAt(new Date(System.currentTimeMillis()
                                + (post.getExpirationTime() * 60000)));

                PostEntity createdPost = postRepository.save(postEntity);

                PostDto postToReturn = mapper.map(createdPost, PostDto.class);

                return postToReturn;
        }

        @Override
        public List<PostDto> getLastPosts() {

                long publicExposureId = 2;
                List<PostEntity> postsEntities = postRepository.getLastPublicPosts(
                                publicExposureId,
                                new Date(System.currentTimeMillis()));

                List<PostDto> postDtos = new ArrayList<>();

                for (PostEntity post : postsEntities) {

                        PostDto postDto = mapper.map(post, PostDto.class);
                        postDtos.add(postDto);

                }
                return postDtos;

        }

        @Override
        public PostDto getPost(String postId) {

                PostEntity postEntity = postRepository.findByPostId(postId);
                PostDto postDto = mapper.map(postEntity, PostDto.class);

                return postDto;
        }

        @Override
        public void deletePost(String postId, long userId) {

                PostEntity postEntity = postRepository.findByPostId(postId);

                if (postEntity.getUser().getId() != userId) {
                        throw new RuntimeException("No tienes permisos para realizar esta acción");

                }

                postRepository.delete(postEntity);

        }

        @Override
        public PostDto updatePost(
                        String postId,
                        long userId,
                        PostCreationDto postUpdateDto) {

                PostEntity postEntity = postRepository.findByPostId(postId);

                if (postEntity.getUser().getId() != userId) {
                        throw new RuntimeException(
                                        "No tienes permisos para realizar esta acción");

                }

                ExposureEntity exposureEntity = exposureRepository
                                .findById(postUpdateDto.getExposureId());

                postEntity.setExposure(exposureEntity);
                postEntity.setTitle(postUpdateDto.getTitle());
                postEntity.setContent(postUpdateDto.getContent());
                postEntity.setExpiresAt(new Date(System.currentTimeMillis()
                                + (postUpdateDto.getExpirationTime() * 60000)));

                PostEntity updatePost = postRepository.save(postEntity);

                PostDto postDto = mapper.map(updatePost, PostDto.class);
                return postDto;
        }

}
