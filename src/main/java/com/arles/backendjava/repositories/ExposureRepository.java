package com.arles.backendjava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.arles.backendjava.entities.ExposureEntity;

@Repository
public interface ExposureRepository
                extends CrudRepository<ExposureEntity, Long> {

        ExposureEntity findById(long id);

}
