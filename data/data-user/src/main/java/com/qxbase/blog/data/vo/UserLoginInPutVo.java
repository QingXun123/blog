package com.qxbase.blog.data.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserLoginInPutVo {
    private String email;

    private String password;
}
