package com.example.userservice.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude
public class ResponseJobShort {
    private String jobsNo; // 공고번호
    private String corpNo; //기업코드?
    //private String empNo; // 인사담당자코드
    private String jobsTitle; //제목
    //private String jobsContext; //내용
    private Integer recruitNum; //채용인원
    private String favoriteLang;
    private String jobLocation;
    private String jobType;
    private String jobQualify;
    private Date applyStart;
    private String employType;
    private String workDetail; //업무내용
    private Date applyEnd; //지원마감일
    private String corpName;
}
