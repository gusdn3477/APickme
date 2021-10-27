package com.example.userservice.vo;


import lombok.Data;

import java.util.Date;

@Data
public class ResponseApply {

    private String applyNum;
    private String applyName;
    private String jobsNo;
    private String applyEmail;
    private Date applyDateTime;

}
