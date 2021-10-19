package com.example.processservice.vo;

import lombok.Data;

@Data
public class RequestPutWritten {

    private String applyNum;
    private String userId;
    private String empNo;
    private String jobsNo;
    private Integer writtenScore;
    private String writtenResult;
    private Integer count;
}
