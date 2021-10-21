package com.example.hrservice.vo;

import lombok.Data;

import java.util.List;

@Data
public class ResponseUser {
    private String empNo; //uuid
    private String email;
    private String name;
    private String nickname;
    private String parents;
    private String auth;

    private List<ResponsePc> pcs;
}
