package com.example.processservice.vo;

import lombok.Data;

@Data
public class RequestFirstInterviewResult {
    private String applyNum;
    private String userId;
    private String jobsNo;
    private Integer count;
    private String firstInterviewResult;
}
