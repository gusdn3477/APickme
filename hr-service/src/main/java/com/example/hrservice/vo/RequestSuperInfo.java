package com.example.hrservice.vo;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class RequestSuperInfo {

    @Email
    @NotNull
    private String email;
    private String name;
    @NotNull
    private String pwd;
    @NotNull
    private String corpName;
    //private String parents;

}
