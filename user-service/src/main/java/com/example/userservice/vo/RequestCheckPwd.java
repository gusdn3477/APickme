package com.example.userservice.vo;

import lombok.Data;

@Data
public class RequestCheckPwd {
    private String userId;
    private String password;
}
