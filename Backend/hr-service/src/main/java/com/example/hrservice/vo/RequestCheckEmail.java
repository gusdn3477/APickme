package com.example.hrservice.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestCheckEmail {

    @NotNull
    private  String email;
}
