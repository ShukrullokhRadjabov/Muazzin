package io.mimsoft.repository;

import io.mimsoft.entity.ProfileEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, String> {
    Optional<ProfileEntity> findByPhone(String phone);
    Optional<ProfileEntity> findByPhoneAndPassword(String phone, String password);

    boolean existsByPhone(String phone);
}
