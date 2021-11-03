package com.example.job.vo;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class RequestJobInfo {
//    private String jobsNo;
    private String corpNo; //기업코드?
    private String empNo;
    private String jobsTitle;
    private String jobsContext;
    @NotNull
    private Integer recruitNum;
    private String favoriteLang;
    private String jobLocation;
    private String jobType; // 고용 유형
    private String employType; // 고용 형태
    private String jobQualify;
    private String workDetail; //업무내용
    private Date applyStart;
    private Date applyEnd; //지원마감일
    private Date intv1Start; //면접1시작일
    private Date intv1End;
    private Date intv2Start;
    private Date intv2End;
    private String closed;

    //밑에는 전형 관련
    private Float writtenMultiple;
//    private Integer writtenPass;
    private Float intv1Multiple;
//    private Integer intv1Pass;
    private Float intv2Multiple;
//    private Integer intv2Pass;

}
