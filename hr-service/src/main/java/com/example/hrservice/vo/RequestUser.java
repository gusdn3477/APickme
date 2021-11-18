package com.example.hrservice.vo;

import lombok.Data;

@Data
public class RequestUser {
    private String empNo;
    private String pwd;
}
//delete에서 사용, pwdCheck에서도 사용