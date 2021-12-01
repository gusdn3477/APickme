package com.example.userservice.vo;


import lombok.Data;

import java.util.Date;

@Data
public class ResponseApply {

    private String applyNum; //uuid

    private String userId;
    private String jobsNo; //uuid로 공고쪽에서 이미 생성되었을것임
    private String applyName;
    private String applyEmail;
    private String applyContact;
    private String portfolio;
    private Date applyDateTime;

}
