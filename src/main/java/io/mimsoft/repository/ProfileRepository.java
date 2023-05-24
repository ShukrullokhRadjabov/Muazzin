package io.mimsoft.repository;

import io.mimsoft.entity.ProfileEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, String> {
    Optional<ProfileEntity> findByPhone(String phone);

    Optional<ProfileEntity> findByPhoneAndPassword(String phone, String password);

    boolean existsByPhone(String phone);

    @Transactional
    @Modifying
    @Query(value = "update profile set session_code =:code where phone =:phone", nativeQuery = true)
    int logoutProfile(@Param("code")String code, @Param("phone") String phone);

}
