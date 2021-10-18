package com.example.hrservice.dto;

import lombok.Data;


@Data
public class HrDto {

    private String empNo; //uuid
    private String corpNo; //uuid
    private String email;
    private String name;
    private String pwd;
    private String encryptedPwd;
    private String nickname;
    private String corpName;
    private String parents;
    private String auth;
}
