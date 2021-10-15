package com.example.userservice.dto;

import com.example.userservice.vo.ResponseOrder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private String email;
    private String name;
    private String pwd;
    private String userId;
    private String address;
    private String phone;
    private LocalDateTime createdAt;

    private String decryptedPwd;
    private String encryptedPwd;

    private List<ResponseOrder> orders;
}
