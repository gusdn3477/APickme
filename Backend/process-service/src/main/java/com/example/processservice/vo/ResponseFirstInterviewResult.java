package com.example.processservice.vo;

import lombok.Data;

@Data
public class ResponseFirstInterviewResult {
    private String applyNum;
    private String userId;
    private String firstInterviewer;
    private String firstInterviewResult;
    private String secondInterviewer;
    private String secondInterviewResult;

}
