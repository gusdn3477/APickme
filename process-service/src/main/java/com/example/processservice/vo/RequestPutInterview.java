package com.example.processservice.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RequestPutInterview {
    private String applyNum;
    private String userId;
    private String empNo;
    private Date firstInterview;
    private String firstInterviewer;
    private Integer firstInterviewScore;
    private String firstInterviewResult;
    private Date secondInterview;
    private String secondInterviewer;
    private Integer secondInterviewScore;
    private String secondInterviewResult;
}
