package com.example.processservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InterviewDto implements Serializable {

    private String applyNum;
    private String userId;
    private String empNo;
    private Date firstInterviewDate;
    private String firstInterviewer;
    private Integer firstInterviewScore;
    private String firstInterviewResult;
    private Date secondInterviewDate;
    private String secondInterviewer;
    private Integer secondInterviewScore;
    private String jobsNo;
    private String secondInterviewResult;
    private Integer count;
    private String firstCheck;
    private String secondCheck;

}
