package com.example.hrservice.service;

import com.example.hrservice.dto.HrDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface HrService extends UserDetailsService {
    HrDto createSuperUser(HrDto hrDto);

}
