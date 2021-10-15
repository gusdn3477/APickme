package com.example.userservice.vo;

import lombok.Data;

@Data
public class RequestUpdateUser {
    private String name;
    private String pwd;
    private String address;
    private String phone;
}
