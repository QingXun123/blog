package com.qxbase.blog.data.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRegisterInPutVo {

    private String phone;

    private String email;

    private String userName;

    private Integer sex;

    private String password;
}
