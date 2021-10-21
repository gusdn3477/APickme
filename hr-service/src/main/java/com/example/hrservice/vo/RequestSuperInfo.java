package com.example.hrservice.vo;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class RequestSuperInfo {

    @Email
    @NotNull
    private String email;
    private String name;
    @NotNull
    private String pwd;
    private String nickname;
    @NotNull
    private String corpName;
    //private String parents;

}
