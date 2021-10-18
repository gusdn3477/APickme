package com.example.processservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InterviewDto implements Serializable {

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
    private Integer jobsNo;
    private String secondInterviewResult;
}
