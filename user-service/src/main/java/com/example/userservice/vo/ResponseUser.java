package com.example.userservice.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResponseUser {
    private String email;
    private String name;
    private String userId;
    private String address;
    private String phone;
    private LocalDateTime createdAt;

    private List<ResponseOrder> orders;
}
