package com.example.job.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude
public class ResponseJob {

    private String jobsNo; // 공고번호
    private String corpNo; //기업코드?
    private String empNo; // 인사담당자코드
    private String jobsTitle; //제목
    private String jobsContext; //내용
    private Integer recruitNum; //채용인원
    private String favoriteLang;
    private String jobLocation;
    private Date createdAt;
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
    private String closed;
}
