package com.arles.backendjava.repositories;

import org.springframework.data.repository.CrudRepository;

import com.arles.backendjava.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

}
