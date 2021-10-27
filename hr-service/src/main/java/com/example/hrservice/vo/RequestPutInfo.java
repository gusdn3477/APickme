package com.example.hrservice.vo;

import lombok.Data;

@Data
public class RequestPutInfo {

    // 식별용
    private String empNo;
    // update 값
    private String name;
    private String pwd;
}
