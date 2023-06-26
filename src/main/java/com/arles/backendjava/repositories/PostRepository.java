package com.arles.backendjava.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arles.backendjava.entities.PostEntity;

@Repository
public interface PostRepository
                extends PagingAndSortingRepository<PostEntity, Long> {

        List<PostEntity> getByUserIdOrderByCreatedAtDesc(long userId);

        @Query(value = "SELECT * FROM POST p WHERE p.exposure_id =:exposure and p.expires_at>:now ORDER BY created_at DESC LIMIT 20", nativeQuery = true)
        List<PostEntity> getLastPublicPosts(
                        @Param("exposure") long exposureId,
                        @Param("now") Date now);

        PostEntity save(PostEntity postEntity);

        PostEntity findByPostId(String postID);

        void delete(PostEntity postEntity);

}
