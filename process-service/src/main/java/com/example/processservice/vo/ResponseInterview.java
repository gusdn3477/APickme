package com.example.processservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseInterview {
    private String applyNum;
    private String userId;
    private String empNo;
    private String jobsNo;
    private Date firstInterview;
    private String firstInterviewer;
    private Integer firstInterviewScore;
    private String firstInterviewResult;
    private Date secondInterview;
    private String secondInterviewer;
    private Integer secondInterviewScore;
    private String secondInterviewResult;
}
