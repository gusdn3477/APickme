package com.example.job.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.Data;

@Data
@JsonInclude
public class RequestDeleteJob {
    @NotNull
    private String empNo;
    @NotNull
    private String jobsNo;
}
