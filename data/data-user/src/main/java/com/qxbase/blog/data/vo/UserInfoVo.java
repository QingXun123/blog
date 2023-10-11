package com.qxbase.blog.data.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserInfoVo {
    @TableId(type = IdType.AUTO)
    private Long userId;

    private String phone;

    private String email;

    private String userName;

    private Integer sex;
}
