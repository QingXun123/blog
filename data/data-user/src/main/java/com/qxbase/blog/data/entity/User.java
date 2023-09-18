package com.qxbase.blog.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
@TableName("t_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long userId;

    private String phone;

    private String email;

    private String userName;

    private Integer sex;

    private String password;

    private Timestamp createTime;

    private Timestamp updateTime;
}
