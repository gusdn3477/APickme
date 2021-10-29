package com.example.job.vo;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class RequestJobInfo {
    private String jobsNo;
    private String corpNo; //기업코드?
    private String empNo;
    private String jobsTitle;
    private String jobsContext;
    @NotNull
    private Integer recruitNum;
    private String favoriteLang;
    private String jobLocation;
    private String jobType;
    private String jobQualify;
    private Date applyStart;
    private String employType;
    private String workDetail; //업무내용
    private Date applyEnd; //지원마감일
    private Date intv1Start; //면접1시작일
    private Date intv1End;
    private Date intv2Start;
    private Date intv2End;

    //밑에는 전형 관련
    private float writtenMultiple;
    private Integer writtenPass;
    private float intv1Multiple;
    private Integer intv1Pass;
    private float intv2Multiple;
    private Integer intv2Pass;

}
