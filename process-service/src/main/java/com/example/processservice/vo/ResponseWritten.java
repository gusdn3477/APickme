package com.example.processservice.vo;

import lombok.Data;

@Data
public class ResponseWritten {
    private String applyNum;
    private String userId;
    private String empNo;
    private String jobsNo;
    private Integer writtenScore;
    private String writtenResult;
}
