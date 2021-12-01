package com.example.userservice.oauth;

import com.example.userservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email); // 이미 email을 통해 생성된 사용자인지 체크
}