package com.example.userservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplyDto {

    private String applyEmail;
    private String applyName;
    private String jobsNo;
    private String userId;
    private String applyNum;
    private String applyContact; // 핸드폰 번호임
    private String portfolio;
    private Date applyDateTime;
}
