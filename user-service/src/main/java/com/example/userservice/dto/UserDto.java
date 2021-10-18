package com.example.userservice.dto;

import com.example.userservice.vo.ResponseOrder;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String email;
    private String name;
    private String pwd;
    private String userId;
    private Date createdAt;

    private String decryptedPwd;
    private String encryptedPwd;

    private List<ResponseOrder> orders;
}