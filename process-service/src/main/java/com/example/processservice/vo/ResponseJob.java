package com.example.processservice.vo;

import lombok.Data;

@Data
public class ResponseJob {
    //id도 필요할진 모르겠음일단은
    private Long id;
    private String jobsNo;
    private String jobsTitle;
    private String jobType;
    private String jobLocation;
    private String employType;
    //일단은 string으로 받을예정
    private String applyEnd;
}
