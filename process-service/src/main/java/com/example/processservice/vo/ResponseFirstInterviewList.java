package com.example.processservice.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseFirstInterviewList {
    private String applyNum;
    private String userId;
    private String empNo;
    private String jobsNo;
    private Date firstInterviewDate;
    private String firstInterviewer;
    private Integer firstInterviewScore;
    private String firstInterviewResult;
}
