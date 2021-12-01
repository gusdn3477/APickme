package com.example.userservice.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {
    private String email;
    private String name;
    private String userId;
    private String address;
    private String phoneNum;
    private String registerDate;
}

   // 리스트로 사용자 이름, 이메일, 가입일, 휴대폰 번호 등 출력
