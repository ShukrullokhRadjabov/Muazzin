package io.mimsoft.repository;

import io.mimsoft.entity.AsmaUlHusnaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AsmaUlHusnaRepository extends CrudRepository<AsmaUlHusnaEntity, Long> {
    List<AsmaUlHusnaEntity> findAll();
}
