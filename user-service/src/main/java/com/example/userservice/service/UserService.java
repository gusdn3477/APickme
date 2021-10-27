package com.example.userservice.service;

import com.example.userservice.dto.ApplyDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.ApplyEntity;
import com.example.userservice.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    UserDto getUserDetailsByEmail(String email);

    /* 전체 사용자 목록 반환 */
    Iterable<UserEntity> getUserByAll();

    /*일반사용자(지원자) 탈퇴*/
    boolean deleteUser(String userId, String email, String password);

    /* 일반사용자(지원자) 수정*/
    UserDto updateByUserId(UserDto userDto, UserDto userDetails);

    ApplyDto createApply(ApplyDto applyDto);

    boolean deleteApply(String jobsNo, String comfirmPassword, String password, String userId);

    ApplyDto getApplyByJobsNo(String jobsNo);

    ApplyDto updateByJobsNo(ApplyDto applyDto, ApplyDto applyDetails);

    ApplyDto getApplyByUserId(String userId);

    Iterable<ApplyEntity> getApplyByAll();

    /* 지원 삭제*/
   
    /*비밀번호 찾기*/
    void findPwd(String email);
}

