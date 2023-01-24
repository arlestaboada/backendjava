package com.arles.backendjava;

import org.springframework.data.repository.CrudRepository;

import com.arles.backendjava.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

}
