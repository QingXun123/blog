package com.qxbase.blog.data.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.qxbase.blog.server.data.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class ChatUserInfoVo extends BaseEntity {
    private Long userId;

    private String phone;

    private String email;

    private String userName;

    private Integer sex;

    private Integer status;

    public ChatUserInfoVo(String userName) {
        this.userName = userName;
        this.status = 0;
    }
}
