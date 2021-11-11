package com.example.processservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWritten {
    private String applyNum;
    private String userId;
    private String empNo;
    private String jobsNo;
    private Integer writtenScore;
    private String writtenResult;
    private String writtenCheck;
}
