package com.example.job.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude
public class ResponseJob {
    @NotNull
    private Long jobsNo; // 공고번호
//    private String corpNo2; //기업코드?
    private String jobsTitle; //제목
    private String jobsContext; //내용
    private Integer recruitNum; //채용인원
    private String favoriteLang;
    private String jobLocation;
    private Date createdAt;
}
