package com.example.processservice.vo;

import lombok.Data;

@Data
public class ResponseInterviewFinal {

    private Integer FirstInterviewScore;
    private Integer SecondInterviewScore;
    private String applyName;
    private String applyContact;
    private String applyEmail;
    private String applyNum;
    private Integer writtenScore;
    private String secondInterviewResult;

}
