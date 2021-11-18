package com.example.userservice.oauth;

import com.example.userservice.entity.UserEntity;
import lombok.Getter;

import java.io.Serializable;

/**
 * 직렬화 기능을 가진 User클래스
 */
@Getter
public class SessionUser implements Serializable {
    private String applyName;
    private String email;

    public SessionUser(UserEntity user){
        this.applyName = user.getApplyName();
        this.email = user.getEmail();
    }
}