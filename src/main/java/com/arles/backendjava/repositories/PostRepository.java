package com.arles.backendjava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.arles.backendjava.entities.PostEntity;

@Repository
public interface PostRepository
        extends CrudRepository<PostEntity, Long> {

}
