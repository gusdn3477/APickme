package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class RequestFindPwd {
    @Email
    private String email;
    private String phoneNum;
}
