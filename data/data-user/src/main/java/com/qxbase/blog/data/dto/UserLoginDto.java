package com.qxbase.blog.data.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserLoginDto {
    private String email;

    private String password;
}
