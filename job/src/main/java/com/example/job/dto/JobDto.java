package com.example.job.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class JobDto implements Serializable{
    private Long jobsNo; // 공고번호
//    private String corpNo2; //기업코드?
    private String jobsTitle; //제목
    private String jobsContext; //내용
    private Integer recruitNum; //채용인원
    private String favoriteLang;
    private String jobLocation;
    private Date createdAt;

}
