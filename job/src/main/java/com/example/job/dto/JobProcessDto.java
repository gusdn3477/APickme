package com.example.job.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class JobProcessDto implements Serializable {
    private String jobsNo;
    private Integer recruitNum;
    private float writtenMultiple;
    private Integer writtenPass;
    private float intv1Multiple;
    private Integer intv1Pass;
    private float intv2Multiple;
    private Integer intv2Pass;

}
