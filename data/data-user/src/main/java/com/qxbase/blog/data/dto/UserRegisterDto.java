package com.qxbase.blog.data.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRegisterDto {

    private String phone;

    private String email;

    private String userName;

    private Integer sex;

    private String password;

    private String identifyCode = "";
}
