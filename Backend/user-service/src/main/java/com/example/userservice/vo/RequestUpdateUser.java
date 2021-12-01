package com.example.userservice.vo;

import lombok.Data;

@Data
public class RequestUpdateUser {
    private String userId;
    private String password;
    private String name;
    private String address;
    private String phoneNum;
}

