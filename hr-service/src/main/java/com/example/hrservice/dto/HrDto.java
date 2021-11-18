package com.example.hrservice.dto;

import com.example.hrservice.vo.ResponsePc;
import lombok.Data;

import java.util.List;


@Data
public class HrDto {

    private String empNo; //uuid
    private String corpNo; //uuid
    private String email;
    private String name;
    private String pwd;
    private String encryptedPwd;
    private String nickname;
    private String corpName;
    private String parents;
    private String auth;

    private List<ResponsePc> pcs;
}
