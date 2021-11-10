package com.example.processservice.vo;

import lombok.Data;

@Data
public class RequestSecondInterviewResult {
    private String applyNum;
    private String userId;
    private String secondInterviewResult;
    private String secondInterviewer;
    private Integer count;
    private String jobsNo;
}
