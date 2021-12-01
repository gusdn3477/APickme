package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUpdateApply {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less than two characters")
    @Email
    private String applyEmail;
    @NotNull
    private String applyName;
    @NotNull
    private String jobsNo;
    @NotNull
    private String userId;

    private String applyContact; // 핸드폰 번호임

    private String portfolio;
}
