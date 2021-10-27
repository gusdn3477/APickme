package com.example.hrservice.service;

import com.example.hrservice.dto.HrDto;
import com.example.hrservice.entity.HrEntity;
import com.example.hrservice.vo.RequestCheckPwd;
import com.example.hrservice.vo.RequestUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface HrService extends UserDetailsService {
    HrDto createSuperUser(HrDto hrDto);
    HrDto createNormalUser(String empNo, HrDto hrDto);

    List<HrEntity> getNormalsAll(String empNo);

    HrDto getNormalById(String empNo);

    void deleteNormal(String empNo);

    HrDto getUserDetailsByEmail(String email);

    void updateNormal(HrDto hrDto);

    void findPwd(String email);

    Boolean getSimpleById(RequestUser checkPwdInfo);
}
