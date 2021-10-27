package com.example.userservice.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseApplyDetail {
    private String applyNum;
    private String applyName;
    private String jobsNo;
    private String applyEmail;
    private Date applyDateTime;

}
//회사명, 공고명, 공고 기간, 채용유형, 고용형태, 근무지역, 공고번호 + 우대사항 등 테이블에 있는 모든 내용 + 결과가 나왔다면 결과까지(진행중/상태 등)