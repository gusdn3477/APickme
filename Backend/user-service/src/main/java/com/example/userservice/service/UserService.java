package com.example.userservice.service;

import com.example.userservice.dto.ApplyDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.ApplyEntity;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.vo.ResponseApplyCount;
import com.example.userservice.vo.RequestUserApply;
import com.example.userservice.vo.ResponseJobShort;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    UserDto getUserDetailsByEmail(String email);

    /* 전체 사용자 목록 반환 */
    Iterable<UserEntity> getUserByAll();
    /*일반사용자(지원자) 탈퇴*/
    boolean deleteUser(String userId, String email, String password);
    /* 일반사용자(지원자) 수정*/
    UserDto updateByUserId(UserDto userDto);
    ApplyDto createApply(ApplyDto applyDto);
    /*비밀번호 찾기*/
    void findPwd(String email);
    Iterable<ApplyEntity> getApplyByAll();
    Iterable<ApplyEntity> getJobsAllApply(String jobsNo);
    void deleteApply(String userId, String jobsNo);
//    boolean deleteApply(String jobsNo, String comfirmPassword, String password, String userId);

    ApplyDto getApplyByJobsNo(String jobsNo);
    ApplyDto updateByJobsNo(ApplyDto applyDto, ApplyDto applyDetails);
    ApplyDto getApplyByUserId(String userId);
    boolean checkPwd(UserDto dto);
    boolean checkEmail(String email);
    List<ResponseJobShort> getJobsByUserId(String userId);
    List<ResponseJobShort> getJobsByUserIdOrderByApplyDateTime(String userId);
    /* 공고별 apply세기*/
    List<ResponseApplyCount> getApplysByCorpNo(String corpNo);
    Iterable<ApplyEntity> getApplys(String userId);
    ApplyDto getApply(RequestUserApply info);

}

