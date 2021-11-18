package com.example.userservice.vo;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {

    @Id
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less than two characters")
    @Email
    private String email;

    @NotNull
    @Size(min = 8, message = "Password must be equal or grater than 8 characters")
    private String password;

    @NotNull
    @Size(min = 8, message = "Password must be equal or grater than 8 characters")
    private String confirmPassword;

    private String phoneNum;

    @NotNull
    private String applyName;
    @Nullable
    private String address;

//    @NotNull
//    @Size(min = 2, message = "Name not be less than two characters")
//    private String name;

}


