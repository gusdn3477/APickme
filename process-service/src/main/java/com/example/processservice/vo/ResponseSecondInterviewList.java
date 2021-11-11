package com.example.processservice.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseSecondInterviewList {
    private String applyNum;
    private String userId;
    private String empNo;
    private String jobsNo;
    private Date secondInterviewDate;
    private String secondInterviewer;
    private Integer secondInterviewScore;
    private String secondInterviewResult;
    private String firstInterviewResult; // <=======이거 없어서 값을 못받아왔었음=================================

}
