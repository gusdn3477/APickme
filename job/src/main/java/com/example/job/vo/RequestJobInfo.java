package com.example.job.vo;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class RequestJobInfo {
    @NotNull
    private Long jobsNo;
    private String jobsTitle;
    private String jobsContext;
    @NotNull
    private Integer recruitNum;
    private String favoriteLang;
    private String jobLocation;
}
