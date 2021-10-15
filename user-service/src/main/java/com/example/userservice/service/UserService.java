package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);
    UserDto getUserDetailsByEmail(String email);

    /* 전체 사용자 목록 반환 */
    Iterable<UserEntity> getUserByAll();

    /* 유저 수정*/
    UserDto updateByUserId(UserDto userDto, UserDto userDetails);

    /*유저 탈퇴*/
    void deleteUser(String userId);
}
