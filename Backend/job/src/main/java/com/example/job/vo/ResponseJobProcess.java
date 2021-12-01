package com.example.job.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseJobProcess {

    //밑에는 전형 관련
    private Float writtenMultiple;
    private Integer writtenPass;
    private Float intv1Multiple;
    private Integer intv1Pass;
    private Float intv2Multiple;
    private Integer intv2Pass;
    private Integer recruitNum;
    private Date writtenDate;
}
