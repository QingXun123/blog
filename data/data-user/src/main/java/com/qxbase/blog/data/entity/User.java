package com.qxbase.blog.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qxbase.blog.server.data.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@ToString
@TableName("t_user")
@Accessors(chain = true)
public class User extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long userId;

    private String phone;

    private String email;

    private String userName;

    private Integer sex;

    private String password;
}
