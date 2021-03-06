package com.example.job.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class CreateJobDto {
    private String jobsNo; // 공고번호
//    private String corpNo; //기업코드?
    private String jobsTitle; //제목
    private String jobsContext; //내용
    private Integer recruitNum; //채용인원
    private String favoriteLang;
    private String jobLocation;
    private Date createdAt;
    private String jobType; //
    private String jobQualify;//지원자격
    private String employType; // 채용유형
    private Date applyStart; //지원시작일

}
